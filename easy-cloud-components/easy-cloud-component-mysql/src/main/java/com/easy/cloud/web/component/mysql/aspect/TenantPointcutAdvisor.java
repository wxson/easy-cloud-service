package com.easy.cloud.web.component.mysql.aspect;

import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.constants.MysqlConstant;
import com.easy.cloud.web.component.mysql.utils.JpaFilterUtil;
import com.easy.cloud.web.component.mysql.utils.TenantMethodFilterHolder;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * 表全局租户AOP
 *
 * @author GR
 * @date 2023/11/10 11:58
 */
@Slf4j
@Component
public class TenantPointcutAdvisor extends DefaultPointcutAdvisor {

  @PersistenceContext
  private EntityManager entityManager;

  public TenantPointcutAdvisor() {
    super.setPointcut(new AnnotationMatchingPointcut(EnableTenant.class, true));
    super.setOrder(2);
    super.setAdvice(new TenantAdvice() {
      @Override
      public void before(Method method, Object[] objects, Object o) throws Throwable {
        // 当前方法禁用租户功能，关闭租户功能
        if (TenantMethodFilterHolder.matchMethod(method)) {
          Session session = entityManager.unwrap(Session.class);
          session.disableFilter(MysqlConstant.TENANT_FILTER_NAME);
        } else {
          // 否则开启租户模式
          JpaFilterUtil.setTenantParam(entityManager);
        }
      }

      @Override
      public void afterReturning(Object o, Method method, Object[] objects, Object o1)
          throws Throwable {
        // 每一个方法结束后释放租户Filter
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter(MysqlConstant.TENANT_FILTER_NAME);
      }
    });
  }
}
