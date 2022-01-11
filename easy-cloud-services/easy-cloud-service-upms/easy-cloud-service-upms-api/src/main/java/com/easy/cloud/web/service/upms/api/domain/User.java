package com.easy.cloud.web.service.upms.api.domain;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Fast Java
 * @date 2021-03-16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class User implements IConvertProxy {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身
     */
    private String tenantId;
    /**
     * 微信unionId
     */
    private String unionId;
    /**
     * 苹果ID
     */
    private String appleId;
    /**
     *
     */
    private String province;
    /**
     *
     */
    private String city;
    /**
     *
     */
    private String nickName;
    /**
     *
     */
    private Integer sex;
    /**
     *
     */
    private String district;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 身份证
     */
    private String identity;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String tel;
    /**
     *
     */
    private String avatar;
    /**
     *
     */
    private String email;

    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}