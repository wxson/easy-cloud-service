package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.enums.GlobalConfEnum;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每日活跃度
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class DailyActiveActionServiceImpl implements IActionService {

    private final IGlobalConfService globalConfService;

    private final IGoodsService goodsService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 获取商品详情
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        if (Objects.isNull(goodsDO)) {
            throw new BusinessException("当前商品信息不存在");
        }

        // 判断当前是否已领取过
        Object received = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
        if (Objects.nonNull(received)) {
            throw new BusinessException("今日奖励已领取");
        }

        // 获取当前玩家的活跃值
        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.USER_ACTIVE_KEY, SecurityUtils.getAuthenticationUser().getId()));
        // 当前活跃度
        int currentActive = GlobalConstants.ZERO;
        if (Objects.nonNull(o)) {
            currentActive = Integer.parseInt(o.toString());
        }

        // 若当前玩家的活跃度小于商品的触发值
        if (currentActive < goodsDO.getAliveness()) {
            throw new BusinessException("当前活跃度过低，暂时无法领取");
        }

        // 当天有效，存储活跃度奖励
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
                TimeUnit.MINUTES);
        // 发放奖励
        this.grantPrize(actionDTO, goodsService, memberFeignClientService);
        return true;
    }

    @Override
    public Object list(ActionDTO actionDTO) {
        // 获取所有参与首冲活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }

        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.USER_ACTIVE_KEY, SecurityUtils.getAuthenticationUser().getId()));
        // 当前活跃度
        int currentActive = GlobalConstants.ZERO;
        if (Objects.nonNull(o)) {
            currentActive = Integer.parseInt(o.toString());
        }

        // 商品列表
        int finalCurrentActive = currentActive;
        List<ActionVO> actionList = goodsDOList.stream()
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class)
                            .setGoodsNo(goodsDO.getNo()).setGoodsName(goodsDO.getName()).setHandle(false);
                    if (finalCurrentActive >= goodsDO.getTriggerValue()) {
                        actionVO.setHandle(true);
                    }
                    // 判断当前是否已领取过
                    Object received = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
                    if (Objects.nonNull(received)) {
                        // 不可操作
                        actionVO.setHandle(false);
                    }
                    return actionVO;
                }).collect(Collectors.toList());
        // 构建返回数据
        JSONObject jsonObject = JSONUtil.createObj();
        // 商品列表
        jsonObject.putOpt("goodsList", JSONUtil.parseArray(actionList));
        // 当前活跃度
        jsonObject.putOpt("currentActive", currentActive);
        // 总活跃度
        String totalActive = globalConfService.getGlobalConfValueByKey(GlobalConfEnum.DAILY_TASK_TOTAL_ACTIVE.getKey());
        jsonObject.putOpt("totalActive", totalActive);
        return jsonObject;
    }
}
