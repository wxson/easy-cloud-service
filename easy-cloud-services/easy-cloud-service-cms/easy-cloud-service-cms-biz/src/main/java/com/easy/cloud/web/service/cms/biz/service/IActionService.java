package com.easy.cloud.web.service.cms.biz.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.enums.GoodsTypeEnum;
import com.easy.cloud.web.service.member.api.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.enums.PropertyOriginEnum;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;

import java.util.Objects;
import java.util.Optional;

/**
 * @author GR
 */
public interface IActionService {

    /**
     * 奖励发放
     *
     * @param actionDTO                活动
     * @param goodsService             商品逻辑对象
     * @param memberFeignClientService 会员逻辑对象
     */
    default void grantPrize(ActionDTO actionDTO, IGoodsService goodsService, MemberFeignClientService memberFeignClientService) {
        // 获取商品详情
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        // 商品无效
        if (Objects.isNull(goodsDO)) {
            throw new BusinessException("当前商品信息不存在");
        }

        Integer goodsType = goodsDO.getGoodsType();
        Optional<GoodsTypeEnum> optionalGoodsTypeEnum = GoodsTypeEnum.getInstanceByCode(goodsType);
        if (!optionalGoodsTypeEnum.isPresent()) {
            throw new BusinessException("当前商品类型不存在");
        }

        // 创建会员更新对象
        MemberDTO memberDTO = MemberDTO.build()
                .setOrigin(PropertyOriginEnum.PLAY.getCode())
                .setOrderNo(actionDTO.getOrderNo());
        switch (optionalGoodsTypeEnum.get()) {
            case DIAMOND:
            case PROP_EXCHANGE:
                memberFeignClientService.updateMemberProperty(memberDTO.setDiamond(goodsDO.getDiamondNum().longValue())
                        .setCoupon(goodsDO.getCouponNum().longValue()));
                break;
            case GOLD_COIN:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsDO.getGoldCoinNum().longValue()));
                break;
            case COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setCoupon(goodsDO.getCouponNum().longValue()));
                break;
            case TABLECLOTH:
            case CARD_BACK:
                // 发放商品值背包中
                memberFeignClientService.insertGoodsInBackpack(goodsDO.getNo());
                break;
            case DIAMOND_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setDiamond(goodsDO.getDiamondNum().longValue())
                        .setCoupon(goodsDO.getCouponNum().longValue()));
                break;
            case GOLD_COIN_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsDO.getGoldCoinNum().longValue())
                        .setCoupon(goodsDO.getCouponNum().longValue()));
                break;
            case DIAMOND_GOLD_COIN:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsDO.getGoldCoinNum().longValue())
                        .setDiamond(goodsDO.getDiamondNum().longValue()));
                break;
            case GOLD_COIN_ALIVENESS:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsDO.getGoldCoinNum().longValue()));
                // 活跃度，当用户接收到支付成功后，在每日任务中，调用handle方法，将存储当天的活跃度
                break;
            case DIAMOND_GOLD_COIN_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsDO.getGoldCoinNum().longValue())
                        .setDiamond(goodsDO.getDiamondNum().longValue())
                        .setCoupon(goodsDO.getCouponNum().longValue()));
                break;
            default:
        }
    }

    /**
     * action操作
     * 一般为奖励领取操作，当前支付成功后，前端调取奖励领取
     *
     * @param actionDTO 参数
     * @return java.lang.Object
     */
    Object handle(ActionDTO actionDTO);

    /**
     * 获取action内容列表
     * 一般为用户进入游戏的初始化参数
     *
     * @param actionDTO 参数
     * @return java.lang.Object
     */
    Object list(ActionDTO actionDTO);
}
