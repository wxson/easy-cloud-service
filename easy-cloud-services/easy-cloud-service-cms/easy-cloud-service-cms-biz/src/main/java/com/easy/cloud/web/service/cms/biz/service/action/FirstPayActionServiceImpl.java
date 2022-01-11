package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.ActionAwardDO;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionAwardService;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首冲
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class FirstPayActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final IActionAwardService actionAwardService;

    private final OrderFeignClientService orderFeignClientService;

    private final MemberFeignClientService memberFeignClientService;

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
        
        // 存储领取记录
        actionAwardService.save(ActionAwardDO.builder()
                .userId(SecurityUtils.getAuthenticationUser().getId())
                .goodsNo(actionDTO.getGoodsNo()).build());

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
        // 查询当前商品，判断该用户是否已购买过
        List<String> paidOffGoodsNoList = CollUtil.newArrayList();
        List<OrderVO> orderVOList = orderFeignClientService.selfVerifyPaidOffGoods(goodsDOList.stream().map(GoodsDO::getNo).collect(Collectors.toList())).getData();
        if (CollUtil.isNotEmpty(orderVOList)) {
            paidOffGoodsNoList.addAll(orderVOList.stream().map(OrderVO::getGoodsNo).collect(Collectors.toList()));
        }

        return goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true);
                    if (paidOffGoodsNoList.contains(goodsDO.getNo())) {
                        actionVO.setHandle(false);
                    }
                    return actionVO;
                }).collect(Collectors.toList());
    }
}
