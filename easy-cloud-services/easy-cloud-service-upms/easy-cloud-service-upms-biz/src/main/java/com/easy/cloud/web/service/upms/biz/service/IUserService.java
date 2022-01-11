package com.easy.cloud.web.service.upms.biz.service;


import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.upms.biz.domain.db.UserDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.UserDTO;
import com.easy.cloud.web.service.upms.biz.domain.vo.UserVO;

/**
 * User 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-03-16
 */
public interface IUserService extends IRepositoryService<UserDO>, IRelationUserRoleService {

    /**
     * 校验是否包含特殊角色，并执行用户信息更新操作
     *
     * @param userDTO 用户信息
     */
    void verifyIsContainSpecialRoleAndUpdate(UserDTO userDTO);

    /**
     * 根据登录类型和认证编码获取用户
     *
     * @param type 登录类型
     * @param code 认证编码
     * @return com.easy.cloud.web.service.upms.biz.domain.vo.UserVO
     */
    UserVO loadSocialUser(String type, String code);

    /**
     * 根据传入对象获取用户详情
     *
     * @param type    方式：微信、QQ
     * @param userDTO 传入对象
     * @return com.easy.cloud.web.service.upms.biz.domain.vo.UserVO
     */
    UserVO loadSocialUserByObject(String type, UserDTO userDTO);
}