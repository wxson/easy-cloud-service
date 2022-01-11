package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionDailyGoodsVO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.enums.GlobalConfEnum;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 月卡
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class MonthCardActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final OrderFeignClientService orderFeignClientService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    private final IGlobalConfService globalConfService;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 商品编码不能为空
        if (StrUtil.isBlank(actionDTO.getGoodsNo())) {
            throw new BusinessException("商品编码不能为空");
        }

        // 当前用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 获取月卡商品详情
        GoodsDO monthCardGoodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));

        // 若当前商品包含每日领取商品信息，则表示当前商品为月卡绑定商品，否则为月卡每日领取商品
        if (StrUtil.isNotBlank(monthCardGoodsDO.getDailyGoodsNo())) {
            // 校验是否购买过该月卡，
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

            // 根据时间倒序
            Optional<OrderVO> orderOptional = orderVOList.stream().max(Comparator.comparing(OrderVO::getCreateAt));
            if (orderOptional.isPresent()) {
                // 获取月卡详情
                OrderVO orderVO = orderOptional.get();
                // 校验是否在购买的有效期内
                if (this.monthCardIsExpires(orderVO.getCreateAt(), ActionVO.build())) {
                    throw new BusinessException("当前月卡已过期");
                }
            }
        } else {
            // 月卡绑定的每日领取的商品信息
            GoodsDO dailyGoodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                    .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                    .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
            if (Objects.isNull(dailyGoodsDO)) {
                throw new BusinessException("月卡绑定的每日领取的商品信息有误");
            }

            // 判断当前是否已领取过
            Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), dailyGoodsDO.getNo()));
            if (Objects.nonNull(o)) {
                throw new BusinessException("当日已领取");
            }
        }


        // 当天有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
                TimeUnit.MINUTES);

        // 开启仓库使用权限
        String enableStorageGoodsNo = globalConfService.getGlobalConfValueByKey(GlobalConfEnum.ENABLE_MEMBER_STORAGE_PERMISSION_GOODS_NO.getKey());
        if (StrUtil.isNotBlank(enableStorageGoodsNo) && enableStorageGoodsNo.equals(actionDTO.getGoodsNo())) {
            memberFeignClientService.enableStoragePermission(StatusEnum.START_STATUS.getCode());
        }

        // 奖品发放
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

        // 自费商品列表
        List<GoodsDO> payGoodsList = goodsDOList.stream().filter(goodsDO -> StrUtil.isNotBlank(goodsDO.getDailyGoodsNo())).collect(Collectors.toList());
        // 每日免费领取商品
        List<GoodsDO> dailyGoodsList = goodsDOList.stream().filter(goodsDO -> StrUtil.isBlank(goodsDO.getDailyGoodsNo())).collect(Collectors.toList());

        // 查询当前商品，判断该用户是否已购买过
        List<String> paidOffGoodsNoList = CollUtil.newArrayList();
        List<OrderVO> orderVOList = new ArrayList<>();
        // 自费商品不为空
        if (CollUtil.isNotEmpty(payGoodsList)) {
            orderVOList = Optional.ofNullable(orderFeignClientService.selfVerifyPaidOffGoods(payGoodsList.stream().map(GoodsDO::getNo).collect(Collectors.toList()))
                    .getData())
                    .orElse(new ArrayList<>());
        }

        // 构建订单支付时间Map(结合应用的特点，粗略使用订单创建时间作为生效时间)
        Map<String, String> orderGoodsAndCreateAtMap = orderVOList.stream()
                // 根据时间倒序，获取最近的一次支付订单
                .sorted(Comparator.comparing(OrderVO::getCreateAt).reversed())
                .collect(Collectors.toMap(OrderVO::getGoodsNo, OrderVO::getCreateAt, (c1, c2) -> c1));
        // 若支付订单不为空
        if (CollUtil.isNotEmpty(orderVOList)) {
            paidOffGoodsNoList.addAll(orderVOList.stream().map(OrderVO::getGoodsNo).collect(Collectors.toList()));
        }

        // 构建自费商品与每日商品关系
        Map<String, GoodsDO> goodsNoMap = dailyGoodsList.stream().collect(Collectors.toMap(GoodsDO::getNo, goodsDO -> goodsDO, (g1, g2) -> g1));
        // 构建自费商品
        return payGoodsList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    // 可操作性、不免费、剩余30天
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true).setFree(false).setFreeDay(30L);
                    // 获取每日免费领取的商品信息
                    Optional.ofNullable(goodsNoMap.get(goodsDO.getDailyGoodsNo()))
                            .ifPresent(dailyGoods -> actionVO.setDailyGoods(dailyGoods.convertTo(ActionDailyGoodsVO.class)
                                    .setGoodsNo(dailyGoods.getNo())
                                    // 默认每日礼包不可领取
                                    .setHandle(false)));
                    // 是否已购买过当前商品，若已购买
                    if (paidOffGoodsNoList.contains(goodsDO.getNo())) {
                        this.monthCardIsExpires(orderGoodsAndCreateAtMap.get(goodsDO.getNo()), actionVO);
                        // 未过期，则禁止购买
                        actionVO.setHandle(false);
                    }

                    // 绑定的日商品不存在
                    ActionDailyGoodsVO dailyGoods = actionVO.getDailyGoods();
                    if (Objects.isNull(dailyGoods)) {
                        return actionVO.setHandle(true).setFreeDay(30L);
                    }

                    // 是否免费领取每日礼包
                    if (dailyGoods.getHandle()) {
                        // 获取当前用户领取商品状态,此时读取的并非goodsNo，而是dailyGoodsNo（即每日领取的对应商品ID）
                        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getDailyGoodsNo()));
                        // 若已领取过，则不可操作
                        if (Objects.nonNull(o)) {
                            // 不可操作
                            dailyGoods.setHandle(false);
                        }
                    }

                    return actionVO;
                }).collect(Collectors.toList());
    }

    /**
     * 月卡是否过期
     *
     * @param createTime 订单创建时间
     * @param actionVO   媒介
     * @return java.lang.Boolean
     */
    private Boolean monthCardIsExpires(String createTime, ActionVO actionVO) {
        // 计算剩余免费天数
        String orderCreateAt = createTime;
        // 订单创建时间不为空
        if (StrUtil.isNotBlank(orderCreateAt)) {
            // 订单创建时间
            DateTime orderCreateDateTime = DateUtil.parse(orderCreateAt);
            // 剩余天数
            DateTime currentDateTime = DateUtil.date();
            DateTime endDateTime = DateUtil.offsetDay(orderCreateDateTime, 30);
            int compare = DateUtil.compare(currentDateTime, endDateTime);
            // 月卡未过期
            if (compare <= 0) {
                ActionDailyGoodsVO dailyGoods = actionVO.getDailyGoods();
                if (Objects.nonNull(dailyGoods)) {
                    // 免费可领取
                    dailyGoods.setHandle(true);
                }
                // 剩余天数
                long surplusDay = DateUtil.betweenDay(DateUtil.date(), DateUtil.offsetDay(orderCreateDateTime, 30), false);
                actionVO.setFreeDay(surplusDay);
                return false;
            }
        }
        return true;
    }
}
