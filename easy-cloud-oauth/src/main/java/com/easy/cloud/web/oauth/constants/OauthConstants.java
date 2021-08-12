package com.easy.cloud.web.oauth.constants;

/**
 * @author GR
 * @date 2021-3-29 16:50
 */
public interface OauthConstants {

    /**
     * Security Redis 前缀
     */
    String COMPONENT_SECURITY_REDIS_PREFIX = "easy:cloud:oauth:";

    /**
     * oauth client 认证字段
     */
    String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS + " from auth_client";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ? and deleted = 0 and tenant_id = %s";
}
