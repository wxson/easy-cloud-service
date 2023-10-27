package com.easy.cloud.web.component.core.constants;

/**
 * @author GR
 * @date 2021-3-29 16:50
 */
public class SecurityConstants {

  /**
   * 源
   */
  public static final String ORIGIN = "origin";

  /**
   * 内部源
   */
  public static final String INNER_ORIGIN = "inner";

  /**
   * Security Redis 前缀
   */
  public static final String COMPONENT_SECURITY_REDIS_PREFIX = "easy_cloud:components:security:";

  /**
   * Security Token 前缀
   */
  public static final String COMPONENT_SECURITY_REDIS_TOKEN_PREFIX =
      COMPONENT_SECURITY_REDIS_PREFIX + "token:";

  /**
   * sys_oauth_client_details 表的字段，不包括client_id、client_secret
   */
  public static final String CLIENT_FIELDS =
      "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
          + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
          + "refresh_token_validity, additional_information, autoapprove";

  /**
   * JdbcClientDetailsService 查询语句
   */
  public static final String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
      + " from auth_client_details";

  /**
   * 默认的查询语句
   */
  public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

  /**
   * 按条件client_id 查询
   */
  public static final String DEFAULT_SELECT_STATEMENT =
      BASE_FIND_STATEMENT + " where client_id = ?";

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
  public static final String AUTHORIZATION_USER_CHANEL = "channel";
  public static final String AUTHORIZATION_USER_TENANT = "tenant";

  /**
   * 移动端登录前缀
   */
  public static final String MOBILE_TOKEN_URL_PREFIX = "/mobile/token/*";
  /**
   * APP登录前缀
   */
  public static final String APP_TOKEN_URL_PREFIX = "/app/token/*";
  /**
   * 资源服务器默认bean名称
   */
  public static final String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

  /**
   * 角色前缀
   */
  public static final CharSequence ROLE_PREFIX = "role_";
}
