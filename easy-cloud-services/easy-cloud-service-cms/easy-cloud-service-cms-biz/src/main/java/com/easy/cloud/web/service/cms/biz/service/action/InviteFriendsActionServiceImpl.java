package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.ActionAwardDO;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.db.UserFriendsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionAwardService;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.cms.biz.service.IUserFriendsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 邀请好友
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class InviteFriendsActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final MemberFeignClientService memberFeignClientService;

    private final IUserFriendsService userFriendsService;

    private final IActionAwardService actionAwardService;

    private final UpmsFeignClientService upmsFeignClientService;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 商品信息不能为空
        if (StrUtil.isBlank(actionDTO.getGoodsNo())) {
            throw new BusinessException("当前商品编码不能为空");
        }


        // 获取对应的活动商品
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        // 上传上品编码是否有效
        if (null == goodsDO) {
            throw new BusinessException("当前商品编码有误，请核对信息后再次尝试");
        }


        // 获取当前玩家详情
        User user = upmsFeignClientService.loadUserByUserId(SecurityUtils.getAuthenticationUser().getId()).getData();
        if (Objects.isNull(user)) {
            throw new BusinessException("获取用户详情失败");
        }

        // 获取当前玩家邀请的好友数
        int inviteFriendsCount = userFriendsService.count(Wrappers.<UserFriendsDO>lambdaQuery().eq(UserFriendsDO::getInviteId, user.getAccount()));
        if (inviteFriendsCount < goodsDO.getTriggerValue()) {
            throw new BusinessException("要求好友不足，不满足领取条件");
        }

        // 是否已领取过
        ActionAwardDO actionAwardDO = actionAwardService.getOne(Wrappers.<ActionAwardDO>lambdaQuery()
                .eq(ActionAwardDO::getUserId, SecurityUtils.getAuthenticationUser().getId())
                .eq(ActionAwardDO::getGoodsNo, actionDTO.getGoodsNo()));
        if (Objects.nonNull(actionAwardDO)) {
            throw new BusinessException("当前玩家已领取过奖励");
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
        // 获取所有参与月卡活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }

        // 获取当前玩家详情
        User user = upmsFeignClientService.loadUserByUserId(SecurityUtils.getAuthenticationUser().getId()).getData();
        if (Objects.isNull(user)) {
            throw new BusinessException("获取用户详情失败");
        }

        // 获取当前玩家邀请的好友数
        int inviteFriendsCount = userFriendsService.count(Wrappers.<UserFriendsDO>lambdaQuery().eq(UserFriendsDO::getInviteId, user.getAccount()));
        // 获取奖励领取情况
        List<String> receiveGoodsNoList = actionAwardService.list(Wrappers.<ActionAwardDO>lambdaQuery()
                .eq(ActionAwardDO::getUserId, SecurityUtils.getAuthenticationUser().getId()))
                .stream().map(ActionAwardDO::getGoodsNo).collect(Collectors.toList());

        // 商品顺序对应邀请好友数
        return goodsDOList.stream()
                // 升序
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(false).setForbid(false);
                    // 商品1，领取条件：邀请1人即可
                    if (inviteFriendsCount >= goodsDO.getTriggerValue()) {
                        if (receiveGoodsNoList.contains(goodsDO.getNo())) {
                            actionVO.setHandle(false).setForbid(true);
                        } else {
                            actionVO.setHandle(true).setForbid(false);
                        }
                    }

                    JSONObject jsonObject = JSONUtil.parseObj(actionVO);
                    jsonObject.putOpt("currentInviteNum", Math.min(inviteFriendsCount, goodsDO.getTriggerValue()));
                    return jsonObject;
                }).collect(Collectors.toList());
    }
}
