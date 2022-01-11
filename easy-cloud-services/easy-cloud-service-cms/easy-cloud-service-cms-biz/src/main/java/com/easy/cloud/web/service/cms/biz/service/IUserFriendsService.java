package com.easy.cloud.web.service.cms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.cloud.web.service.cms.biz.domain.db.UserFriendsDO;

/**
 * @author GR
 */
public interface IUserFriendsService extends IService<UserFriendsDO> {
    /**
     * 绑定好友ID
     *
     * @param friendId 好友ID
     * @return java.lang.Boolean
     */
    Boolean bindFriendId(String friendId);
}
