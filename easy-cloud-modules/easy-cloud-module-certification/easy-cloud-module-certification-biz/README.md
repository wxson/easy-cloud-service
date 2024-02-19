# Easy-Cloud-Module-Certification
## 使用步骤
* 导入依赖包
```xml
<dependency>
   <groupId>com.easy.cloud.web</groupId>
   <artifactId>easy-cloud-module-certification-api</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```

* 配置认证适配器,注意，必须是单列Bean，此适配器非必须，根据业务需求使用
>企业认证
```java
@Slf4j
@Component
public class CompanyAuthenticationAdapter implements ICompanyAuthenticationAdapter {

    @Override
    public void authenticationSuccess(String userId) {
        log.info("company authentication success");
    }

    @Override
    public void authenticationFail(String userId) {
        log.info("company authentication fail");
    }
}
```
> 个人认证
```java
@Slf4j
@Component
public class PersonalAuthenticationAdapter implements IPersonalAuthenticationAdapter {

    @Override
    public void authenticationSuccess(String userId) {
        log.info("personal authentication success");
    }

    @Override
    public void authenticationFail(String userId) {
        log.info("personal authentication fail");
    }
}
```
