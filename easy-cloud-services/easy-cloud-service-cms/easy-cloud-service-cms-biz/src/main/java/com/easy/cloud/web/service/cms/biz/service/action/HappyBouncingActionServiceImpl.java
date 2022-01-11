package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
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
 * 欢乐对对碰
 * 欢乐对对碰
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class HappyBouncingActionServiceImpl implements IActionService {

    /**
     * 读取当前欢乐碰次数，当前的redis key为mj-biz模块中的key一一对应，修改时记得同步
     */
    private final String MJ_BOUNCING = "easy:cloud:mj:player:happy:bouncing:{}";

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    private final IGoodsService goodsService;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 免费领取的特价商品编码不能为空，此时的goodsNo为月卡中每日领取的dailyGoodsNo而非真正意义上的goodsNo
        if (StrUtil.isBlank(actionDTO.getGoodsNo())) {
            throw new BusinessException("免费领取的商品编码不能为空");
        }

        // 获取对应的活动商品
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        // 上传上品编码是否有效
        if (null == goodsDO) {
            throw new BusinessException("当前商品编码有误，请核对信息后再次尝试");
        }

        // 判断当前是否已领取过
        Object received = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
        if (Objects.nonNull(received)) {
            throw new BusinessException("当前奖励已领取过");
        }

        // 读取当前欢乐碰次数，当前的redis key为mj-biz模块中的key一一对应，修改时记得同步
        Object o = redisTemplate.opsForValue().get(StrUtil.format(MJ_BOUNCING, SecurityUtils.getAuthenticationUser().getId()));
        // 当前碰牌次数
        int pengCount = 0;
        if (Objects.nonNull(o)) {
            pengCount = Integer.parseInt(o.toString());
        }

        // 碰的次数少于触发值
        if (pengCount < goodsDO.getTriggerValue()) {
            throw new BusinessException("当前尚未满足领取条件");
        }

        // 当天有效
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

        // 读取当前欢乐碰次数，当前的redis key为mj-biz模块中的key一一对应，修改时记得同步
        Object o = redisTemplate.opsForValue().get(StrUtil.format(MJ_BOUNCING, SecurityUtils.getAuthenticationUser().getId()));
        // 当前碰牌次数
        int pengCount = 0;
        if (Objects.nonNull(o)) {
            pengCount = Integer.parseInt(o.toString());
        }

        int finalPengCount = pengCount;
        return goodsDOList.stream()
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(false);
                    // 若当前次数大于触发值，则可以领取
                    if (finalPengCount > goodsDO.getTriggerValue()) {
                        actionVO.setHandle(true);
                    }
                    // 判断当前商品是否已领取过
                    // 判断当前是否已领取过
                    Object received = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
                    if (Objects.nonNull(received)) {
                        // 不可操作
                        actionVO.setHandle(false);
                    }

                    // 放入当前玩家总的碰牌次数
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("totalPengCout", finalPengCount);
                    actionVO.setSpecialData(jsonObject);
                    return actionVO;
                }).collect(Collectors.toList());
    }
}
