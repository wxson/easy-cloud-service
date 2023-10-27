package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
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
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * VIP免费领取
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class CertificationActionServiceImpl implements IActionService {

  private final IGoodsService goodsService;

  private final UpmsFeignClientService upmsFeignClientService;

  private final IActionAwardService actionAwardService;

  private final MemberFeignClientService memberFeignClientService;

  @Override
  public Object handle(ActionDTO actionDTO) {
    // 用户名和身份证号不能为空
    if (StrUtil.isBlank(actionDTO.getUserName()) || StrUtil.isBlank(actionDTO.getIdentity())) {
      throw new BusinessException("用户名和身份证号不能为空");
    }

    // 身份证号错误
    if (!IdcardUtil.isValidCard(actionDTO.getIdentity())) {
      throw new BusinessException("当前输入的身份证信息存在错误");
    }

    // 当前用户ID
    String userId = SecurityUtils.getAuthenticationUser().getId();
    // 获取用户信息
    UserVO user = upmsFeignClientService.loadUserByUserId(userId).getData();
    // 是否已实名，通过身份证号判断
    if (StrUtil.isNotBlank(user.getIdentity())) {
      throw new BusinessException("当前玩家已实名");
    }

    // 实名认证
    Object code = upmsFeignClientService.certification(
        UserBindDTO.builder().id(userId).userName(actionDTO.getUserName())
            .identity(actionDTO.getIdentity()).build()).getCode();
    if (GlobalCommonConstants.ZERO != Integer.parseInt(code.toString())) {
      throw new BusinessException("实名认证失败");
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
    List<GoodsDO> goodsDOList = goodsService.list(
        Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
    if (CollUtil.isEmpty(goodsDOList)) {
      return CollUtil.newArrayList();
    }

    // 获取用户信息
    User user = upmsFeignClientService
        .loadUserByUserId(SecurityUtils.getAuthenticationUser().getId()).getData();
    // 未实名认证
    if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getIdentity())) {
      return goodsDOList.stream()
          .map(goodsDO -> goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo())
              .setHandle(true)).collect(Collectors.toList());
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
          ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo())
              .setHandle(true);
          if (receiveGoodsNoList.contains(goodsDO.getNo())) {
            actionVO.setHandle(false);
          }
          return actionVO;
        }).collect(Collectors.toList());
  }
}
