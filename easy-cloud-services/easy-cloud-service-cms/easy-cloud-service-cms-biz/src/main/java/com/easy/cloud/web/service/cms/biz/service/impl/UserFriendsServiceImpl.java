package com.easy.cloud.web.service.cms.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.UserFriendsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.enums.ActionEnum;
import com.easy.cloud.web.service.cms.biz.enums.GlobalConfEnum;
import com.easy.cloud.web.service.cms.biz.mapper.DbUserFriendsMapper;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import com.easy.cloud.web.service.cms.biz.service.IUserFriendsService;
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserFriendsServiceImpl extends ServiceImpl<DbUserFriendsMapper, UserFriendsDO> implements IUserFriendsService {

    private final UpmsFeignClientService upmsFeignClientService;

    private final IGlobalConfService globalConfService;

    @Override
    public Boolean bindFriendId(String account) {
        if (StrUtil.isBlank(account)) {
            throw new BusinessException("好友ID不能为空");
        }

        // 校验当前好友是否为真实用户
        User friends = upmsFeignClientService.loadUserByAccount(account).getData();
        if (null == friends) {
            throw new BusinessException("当前用户ID不存在，请输入有效的用户ID");
        }

        // 查询是否存在关系
        UserFriendsDO existUserFriendsDO = this.getOne(Wrappers.<UserFriendsDO>lambdaQuery()
                .eq(UserFriendsDO::getUserId, SecurityUtils.getAuthenticationUser().getId())
        );
        if (null != existUserFriendsDO) {
            throw new BusinessException("每人只能被邀请一次");
        }

        boolean save = this.save(UserFriendsDO.builder().userId(SecurityUtils.getAuthenticationUser().getId()).inviteId(account).build());
        if (save) {
            String bindFriendGoodsNo = globalConfService.getGlobalConfValueByKey(GlobalConfEnum.BIND_FRIEND_GOODS_NO.getKey());
            Optional<IActionService> actionServiceOptional = ActionEnum.BIND_FRIENDS.getActionService();
            actionServiceOptional.ifPresent(iActionService -> iActionService.handle(ActionDTO.build().setAction(ActionEnum.BIND_FRIENDS).setGoodsNo(bindFriendGoodsNo)));
        }

        return save;
    }
}
