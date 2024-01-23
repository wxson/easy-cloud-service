package com.easy.cloud.web.service.upms.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * User请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    /**
     * 文档ID
     */
    private String id;

    /**
     * Oauth2.0 code授权模式
     */
    private String code;

    /**
     * 电话编码
     */
    private String phoneCode;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 电话
     */
    private String tel;
}