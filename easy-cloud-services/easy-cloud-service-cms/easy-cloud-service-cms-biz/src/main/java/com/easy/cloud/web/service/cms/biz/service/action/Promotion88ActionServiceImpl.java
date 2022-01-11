package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 88元促销
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class Promotion88ActionServiceImpl implements IActionService {

    private final OrderFeignClientService orderFeignClientService;

    private final MemberFeignClientService memberFeignClientService;

    private final IGoodsService goodsService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 获取当前商品的购买记录
        List<OrderVO> orderVOList = orderFeignClientService.selfVerifyPaidOffGoods(CollUtil.newArrayList(actionDTO.getGoodsNo())).getData();
        if (!CollUtil.isNotEmpty(orderVOList)) {
            throw new BusinessException("当前商品不存在任何支付信息");
        }

        // 当前用户是否已购买过
        long count = orderVOList.stream().filter(orderVO -> orderVO.getUserId().equals(SecurityUtils.getAuthenticationUser().getId())).count();
        if (count == GlobalConstants.ZERO) {
            throw new BusinessException("当前用户未购买过当前商品");
        }

        // 获取所有参与首冲活动的商品
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        if (Objects.isNull(goodsDO)) {
            throw new BusinessException("获取当前活动商品信息失败");
        }

//        // 获取当前用户领取商品状态
//        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
//        if (Objects.nonNull(o)) {
//            throw new BusinessException("当前玩家已领取过奖励");
//        }

        // 当天有效
//        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
//                true,
//                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
//                TimeUnit.MINUTES);

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

        return goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true);
                    // 获取当前用户领取商品状态
//                    Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
//                    if (Objects.nonNull(o)) {
//                        // 不可操作
//                        actionVO.setHandle(false);
//                    }
                    return actionVO;
                }).collect(Collectors.toList());
    }
}
