package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每日补给
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class DailySupplyActionServiceImpl implements IActionService {

    /**
     * 每日最大免费领取次数
     */
    private final int DAILY_FREE_RECEIVE_COUNT = 3;

    private final IGoodsService goodsService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 获取当前领取次数
        int currentCount = (int) Optional.ofNullable(redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()))).orElse(0);
        if (currentCount >= DAILY_FREE_RECEIVE_COUNT) {
            throw new BusinessException("当前免费领取次数已用完");
        }

        // 累计+1
        currentCount += 1;

        // 当天有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                currentCount,
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

        return goodsDOList.stream()
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true);
                    int currentCount = (int) Optional.ofNullable(redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()))).orElse(0);
                    if (currentCount >= DAILY_FREE_RECEIVE_COUNT) {
                        actionVO.setHandle(false);
                    }

                    actionVO.setCurrentValue(currentCount);
                    return actionVO;
                }).collect(Collectors.toList());
    }
}
