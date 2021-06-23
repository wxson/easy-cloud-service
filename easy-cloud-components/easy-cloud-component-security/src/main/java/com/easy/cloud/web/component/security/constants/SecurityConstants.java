package com.easy.cloud.web.component.security.constants;

/**
 * @author GR
 * @date 2021-3-29 16:50
 */
public class SecurityConstants {

    /**
     * Security Redis 前缀
     */
    public static final String COMPONENT_SECURITY_REDIS_PREFIX = "easy:cloud:components:security:";

    /**
     * Security Token 前缀
     */
    public static final String COMPONENT_SECURITY_REDIS_TOKEN_PREFIX = COMPONENT_SECURITY_REDIS_PREFIX + "token:";

    /**
     * Security Jwt token 签名
     */
    public static final String JWT_TOKEN_SIGNING_KEY = "easy.cloud.jwt.secret";
    /**
     * Security Client secret
     */
    public static final CharSequence SECURITY_CLIENT_SECRET = "easy.cloud.client.secret";

    /***************************************认证用户信息增强KEY**********************************/
    public static final String AUTHORIZATION_USER_ID = "user_id";
    public static final String AUTHORIZATION_USER_NAME = "user_name";
    public static final String AUTHORIZATION_USER_CHANEL_ID = "channel_id";
    public static final String AUTHORIZATION_USER_TENANT_ID = "tenant_id";
}
