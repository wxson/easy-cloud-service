package com.easy.cloud.web.component.mysql.aspect;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.utils.JpaFilterUtil;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Jpa AOP
 *
 * @author GR
 * @date 2023/11/10 10:46
 */
@Slf4j
@Aspect
@Component
public class MySqlAspect {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * 用于某些特定环境下，比如某个实体类只有某个方法需要根据租户查询
   *
   * @param joinPoint    切点
   * @param enableTenant 租户注解
   */
  @Before("@annotation(enableTenant)")
  public void before(JoinPoint joinPoint, EnableTenant enableTenant) {
    // 若当前开启租户模式，则设置租户Filter
    if (enableTenant.value()) {
      JpaFilterUtil.setTenantParam(entityManager);
    }
  }

  /**
   * 用于某些特定环境下，比如某个实体类只有某个方法需要根据租户查询
   *
   * @param joinPoint   切点
   * @param enableLogic 逻辑注解
   */
  @Before("@annotation(enableLogic)")
  public void before(JoinPoint joinPoint, EnableLogic enableLogic) {
    JpaFilterUtil.setLogicParam(entityManager);
  }
}
