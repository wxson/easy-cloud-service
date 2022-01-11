package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
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
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 绑定手机号
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class BindPhoneActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final UpmsFeignClientService upmsFeignClientService;

    private final IActionAwardService actionAwardService;

    private final MemberFeignClientService memberFeignClientService;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 手机号不能为空
        if (StrUtil.isBlank(actionDTO.getTel())) {
            throw new BusinessException("当前输入的手机号不能为空");
        }

        // 当前用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 获取用户信息
        User user = upmsFeignClientService.loadUserByUserId(userId).getData();
        // 未绑定手机号
        if (StrUtil.isNotBlank(user.getTel())) {
            throw new BusinessException("当前手机号已绑定");
        }

        // 判断是否是手机号
        if (!PhoneUtil.isMobile(actionDTO.getTel())) {
            throw new BusinessException("当前输入的手机号格式错误");
        }

        // 绑定当前手机号
        Object code = upmsFeignClientService.bindUserTel(User.build().setId(userId).setTel(actionDTO.getTel())).getCode();
        if (GlobalConstants.ZERO != Integer.parseInt(code.toString())) {
            throw new BusinessException("绑定手机号失败");
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

        // 获取用户信息
        User user = upmsFeignClientService.loadUserByUserId(SecurityUtils.getAuthenticationUser().getId()).getData();
        // 未绑定手机号
        if (StrUtil.isBlank(user.getTel())) {
            return goodsDOList.stream()
                    .map(goodsDO -> goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(false)).collect(Collectors.toList());
        }

        // 获取奖励领取情况
        List<String> receiveGoodsNoList = actionAwardService.list(Wrappers.<ActionAwardDO>lambdaQuery()
                .eq(ActionAwardDO::getUserId, SecurityUtils.getAuthenticationUser().getId())
                .eq(ActionAwardDO::getGoodsNo, actionDTO.getGoodsNo()))
                .stream().map(ActionAwardDO::getGoodsNo).collect(Collectors.toList());

        return goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(true);
                    if (receiveGoodsNoList.contains(goodsDO.getNo())) {
                        actionVO.setHandle(false);
                    }
                    return actionVO;
                }).collect(Collectors.toList());
    }
}
