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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 签到
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class SignInActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 判断当前是否已领取过
        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()));
        if (Objects.nonNull(o)) {
            throw new BusinessException("当日已领取");
        }

        // 当日是否签到
        Object signIn = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.CURRENT_DAY_SIGN_IN, SecurityUtils.getAuthenticationUser().getId(), DateUtil.today()));
        if (Objects.nonNull(signIn)) {
            throw new BusinessException("当日已签到");
        }

        // 当日商品领取，一周有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfWeek(DateUtil.date()), DateUnit.DAY),
                TimeUnit.DAYS);
        // 当日签到，一周有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.CURRENT_DAY_SIGN_IN, SecurityUtils.getAuthenticationUser().getId(), DateUtil.today()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfWeek(DateUtil.date()), DateUnit.DAY),
                TimeUnit.DAYS);

        // 发放奖励
        this.grantPrize(actionDTO, goodsService, memberFeignClientService);
        return true;
    }

    @Override
    public Object list(ActionDTO actionDTO) {
        // 获取所有参与月卡活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }

        AtomicReference<Boolean> allow = new AtomicReference<>(false);
        AtomicReference<String> allowGetGoodsNo = new AtomicReference<>("");
        List<ActionVO> actionVOList = goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    // 默认可领取
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true);
                    // 判断当前是否已领取过
                    Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionVO.getGoodsNo()));
                    if (Objects.nonNull(o)) {
                        // 已领取过，不可操作
                        actionVO.setHandle(false);
                    }

                    // 若已存在未签到对象，则返回
                    if (allow.get()) {
                        return actionVO;
                    }

                    // 当日是否签到
                    Object signIn = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.CURRENT_DAY_SIGN_IN, SecurityUtils.getAuthenticationUser().getId(), DateUtil.today()));
                    if (Objects.isNull(signIn)) {
                        allow.set(true);
                        allowGetGoodsNo.set(actionVO.getGoodsNo());
                    }

                    return actionVO;
                }).collect(Collectors.toList());

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("handle", allow.get());
        jsonObject.putOpt("goodsNo", allowGetGoodsNo.get());
        jsonObject.putOpt("list", actionVOList);

        return jsonObject;
    }
}
