package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 特价促销
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class SpecialPromotionActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final OrderFeignClientService orderFeignClientService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 免费领取的特价商品编码不能为空
        if (StrUtil.isBlank(actionDTO.getGoodsNo())) {
            throw new BusinessException("免费领取的特价商品编码不能为空");
        }

        // 获取商品信息
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        // 上传上品编码是否有效
        if (null == goodsDO) {
            throw new BusinessException("当前商品编码有误，请核对信息后再次尝试");
        }

        // 若每日参数有值，则为付费商品，即购买三次，否则为免费领取商品
        if (StrUtil.isNotBlank(goodsDO.getDailyGoodsNo())) {
            // 通过订单获取详情
            if (StrUtil.isBlank(actionDTO.getOrderNo())) {
                throw new BusinessException("当前订单编号不能为空");
            }

            // 获取订单详情
            OrderVO orderVO = orderFeignClientService.getOrderDetailByNo(actionDTO.getOrderNo()).getData();
            if (Objects.isNull(orderVO)) {
                throw new BusinessException("当前订单信息错误");
            }

            // 若订单位置付，则不允许领取
            if (OrderStatusEnum.PAID_OFF.getCode() != orderVO.getOrderStatus()) {
                throw new BusinessException("当前订单未支付状态，无法领取");
            }
        } else {
            // 获取出牌条件商品信息
            GoodsDO specialGoodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                    .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                    .eq(GoodsDO::getDailyGoodsNo, actionDTO.getGoodsNo()));
            if (Objects.isNull(specialGoodsDO)) {
                throw new BusinessException("获取特价商品信息失败");
            }

            // 每日只能购买三次，购买三次成功，触发免费领取额外奖励
            // 获取当前时间
            String beginOfDay = DateUtil.beginOfDay(DateUtil.date()).toString();
            String endOfDay = DateUtil.endOfDay(DateUtil.date()).toString();
            // 获取已支付次数
            List<OrderVO> orderVOList = orderFeignClientService.orderListTimeRangeAndStatus(OrderDTO.build()
                    .setMaxCreateAt(endOfDay)
                    .setMinCreateAt(beginOfDay)
                    .setGoodsNo(specialGoodsDO.getNo())
                    .setOrderStatus(OrderStatusEnum.PAID_OFF.getCode())).getData();
            if (orderVOList.size() < 3) {
                throw new BusinessException("当前为满足领取条件，暂时无法领取");
            }
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
        // 获取当前活动的额商品列表
        // 获取所有参与首冲活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }
        // 每日只能购买三次，购买三次成功，触发免费领取额外奖励
        // 获取当前时间
        String beginOfDay = DateUtil.beginOfDay(DateUtil.date()).toString();
        String endOfDay = DateUtil.endOfDay(DateUtil.date()).toString();
        // 获取已支付次数
        List<OrderVO> orderVOList = orderFeignClientService.orderListTimeRangeAndStatus(OrderDTO.build()
                .setMaxCreateAt(endOfDay)
                .setMinCreateAt(beginOfDay)
                .setUserId(SecurityUtils.getAuthenticationUser().getId())
                .setOrderStatus(OrderStatusEnum.PAID_OFF.getCode())).getData();
        // 构建已购买的商品map
        Map<String, Long> goodsCountMap = new HashMap<>();
        // 订单不为空
        if (CollUtil.isNotEmpty(orderVOList)) {
            goodsCountMap = orderVOList.stream().collect(Collectors.groupingBy(OrderVO::getGoodsNo, Collectors.counting()));
        }

        // 一个自费、一个免费,且自费在前，免费在后
        AtomicBoolean isFree = new AtomicBoolean(false);
        AtomicReference<String> freeGoodsNo = new AtomicReference<>("");
        Map<String, Long> finalGoodsCountMap = goodsCountMap;
        List<ActionVO> actionVOList = goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    // 构建返回商品
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setHandle(true).setGoodsNo(goodsDO.getNo());
                    // 获取每一个商品的订单次数
                    Long goodsNoCount = Optional.ofNullable(finalGoodsCountMap.get(goodsDO.getNo())).orElse(0L);
                    // 付费商品
                    if (StrUtil.isNotBlank(goodsDO.getDailyGoodsNo())) {
                        if (goodsNoCount >= 3) {
                            isFree.set(true);
                            actionVO.setHandle(false);
                        }
                        return actionVO;
                    }

                    // 其他禁止
                    freeGoodsNo.set(actionVO.getGoodsNo());
                    actionVO.setHandle(false);
                    return actionVO;
                }).collect(Collectors.toList());
        // 是否触发免费商品
        for (ActionVO actionVO : actionVOList) {
            // 可以领取免费奖励
            if (isFree.get() && actionVO.getGoodsNo().equals(freeGoodsNo.get())) {
                actionVO.setHandle(true);
                // 判断当前是否已领取过
                Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionVO.getGoodsNo()));
                if (Objects.nonNull(o)) {
                    // 已领取过，不可操作
                    actionVO.setHandle(false);
                }
            }
        }

        return actionVOList.stream()
                .map(actionVO -> {
                    // 获取每一个商品的订单次数
                    Long goodsNoCount = Optional.ofNullable(finalGoodsCountMap.get(actionVO.getGoodsNo())).orElse(0L);
                    JSONObject jsonObject = JSONUtil.parseObj(actionVO);
                    // 购买次数，最大购买次数3次
                    jsonObject.putOpt("purchaseNum", Math.min(goodsNoCount, 3));
                    jsonObject.putOpt("purchaseMaxNum", 3);
                    return jsonObject;
                }).collect(Collectors.toList());
    }
}

