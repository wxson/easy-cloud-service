package com.easy.cloud.web.component.mysql.configuration;

import cn.hutool.core.util.ClassUtil;
import com.easy.cloud.web.component.core.util.SnowflakeUtils;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.utils.TenantMethodFilterHolder;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置租户
 *
 * @author GR
 * @date 2021-10-28 18:21
 */
@Slf4j
@Configuration
@ComponentScan({"com.easy.cloud.web.component.mysql"})
public class MySqlConfiguration {

  /**
   * 扫描过滤掉不需要的租户方法
   * <p>一般租户的启用会在Class上全局注释，但因为业务逻辑导致某个方法中不能携带租户信息，故有此逻辑</p>
   * <p>比如：租户查询平台信息时，因为租户限制，导致无法查询的情况</p>
   */
  @PostConstruct
  public void scanFilterTenantMethod() {
    // 扫描多租户实体对象
    Set<Class<?>> tenantEntities = ClassUtil
        .scanPackageByAnnotation("com.easy.cloud.web", EnableTenant.class);
    for (Class<?> tenantEntity : tenantEntities) {
      // 遍历所有方法
      for (Method declaredMethod : tenantEntity.getDeclaredMethods()) {
        // 获取租户注解
        EnableTenant enableTenant = declaredMethod.getAnnotation(EnableTenant.class);
        // 若关闭租户功能
        if (Objects.nonNull(enableTenant) && !enableTenant.value()) {
          TenantMethodFilterHolder.addFilterTenantMethod(declaredMethod);
        }
      }
    }
  }

//
//    /**
//     * 拦截器
//     *
//     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
//     */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        // 多租户插件
////        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
////        tenantInterceptor.setTenantLineHandler(() -> {
////            // 返回当前用户的租户ID
////            return new LongValue(SecurityUtils.getAuthenticationUser().getTenantId());
////        });
////        interceptor.addInnerInterceptor(tenantInterceptor);
//        // 分页拦截器
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        return interceptor;
//    }

  @Bean
  public IdentifierGenerator identifierGenerator() {
    return (sharedSessionContractImplementor, o) -> {
      log.info("-------分布式ID：{}", SnowflakeUtils.next());
      return SnowflakeUtils.next();
    };
  }
}
