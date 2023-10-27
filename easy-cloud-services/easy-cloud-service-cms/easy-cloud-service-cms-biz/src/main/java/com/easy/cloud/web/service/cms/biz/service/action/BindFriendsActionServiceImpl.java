package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.ActionAwardDO;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.service.IActionAwardService;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.cms.biz.service.IUserFriendsService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 被邀请好友
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class BindFriendsActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final MemberFeignClientService memberFeignClientService;

    private final IUserFriendsService userFriendsService;

    private final IActionAwardService actionAwardService;

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
        return null;
    }
}
