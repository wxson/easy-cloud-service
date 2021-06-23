## 认证模式
### 一、授权码模式
* 授权服务配置：
```
/**
 * Oauth2 授权服务配置
 *
 * @author GR
 * @date 2021-3-29 14:20
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    /**
     * 授权服务配置,主要是为了颁发授权码
     *
     * @param clients 客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 本机内存
                .inMemory()
                // 客户端ID，即应用ID
                .withClient("client")
                // 密钥
                .secret(passwordEncoder.encode("saas-service-scret"))
                // 重定向地址,目的是为了获取授权码(仅授权码模式下使用)
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 自动授权
                .autoApprove(true)
                /*
                 * 授权模式（可同时配置）
                 * authorization_code：授权码模式
                 * password：密码模式
                 * refresh_token: token刷新模式
                 */
                .authorizedGrantTypes("authorization_code");
    }
}
```
* 资源服务配置
```
/**
 * Oauth2 资源服务配置
 *
 * @author GR
 * @date 2021-3-29 14:10
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有请求都进行拦截
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                // 匹配所有资源
                .antMatchers("/**");
    }
}
```
* 浏览器发起请求：
```
http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all
```
* 返回结果：
```
// 其中code为Oauth2颁布的授权码
https://www.baidu.com/?code=yYu72F
```
* 获取token：
```
http://localhost:8080/oauth/token
请求头：Authorization：Basic Y2xpZW50OjEzMjIzMQ==
// 表单
form: 
    grant_type=authorization_code
    client_id=client
    redirect_uri=http://www.baidu.com
    scope=all
    code=yYu72F
//响应
reponse:
{
    "access_token": "df3cd93c-3e52-442a-8674-b5416288b7f3",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "all"
}
```
* 携带token请求资源：
```
http://localhost:8080/test
请求头：
Authorization：Bearer df3cd93c-3e52-442a-8674-b5416288b7f3
```
### 二、密码模式
* AuthorizationServerConfiguration中配置：
```
/**
 * Oauth2 授权服务配置
 *
 * @author GR
 * @date 2021-3-29 14:20
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final ISecurityUserDetailsService securityUserDetailsService;

    /**
     * 密码模式管理
     *
     * @param endpoints 端点控制
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(securityUserDetailsService);
    }

    /**
     * 授权服务配置,主要是为了颁发授权码
     *
     * @param clients 客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 本机内存
                .inMemory()
                // 客户端ID，即应用ID
                .withClient("client")
                // 密钥
                .secret(passwordEncoder.encode("saas-service-scret"))
                // 重定向地址,目的是为了获取授权码(仅授权码模式下使用)
//                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 自动授权
                .autoApprove(true)
                /*
                 * 授权类型
                 * authorization_code：授权码模式
                 * password：密码模式
                 * refresh_token: token刷新模式
                 */
                .authorizedGrantTypes( "password", "refresh_token");
    }
}
```
* 请求token
```
http://localhost:8080/oauth/token
请求头：Authorization：Basic Y2xpZW50OjEzMjIzMQ==
// 表单
form: 
    grant_type=password
    scope=all
    username=admin
    password=123123
//响应
reponse:
{
    "access_token": "b9d6552d-7b12-47d2-b8ab-d2a9ed4a0f8a",
    "token_type": "bearer",
    "refresh_token": "d9ca2f87-03b6-4efb-b0ef-74375a10656c",
    "expires_in": 43199,
    "scope": "all"
}
```
* 请求资源
```
http://localhost:8080/test
请求头：
Authorization：Bearer df3cd93c-3e52-442a-8674-b5416288b7f3
```