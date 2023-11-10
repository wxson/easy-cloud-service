package com.easy.cloud.web.component.mysql.aspect;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.utils.JpaFilterUtil;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * 表全局逻辑删除AOP
 *
 * @author GR
 * @date 2023/11/10 11:58
 */
@Slf4j
@Component
public class LogicPointcutAdvisor extends DefaultPointcutAdvisor {

  @PersistenceContext
  private EntityManager entityManager;

  public LogicPointcutAdvisor() {
    super.setPointcut(new AnnotationMatchingPointcut(EnableLogic.class, true));
    super.setOrder(1);
    super.setAdvice((MethodBeforeAdvice) (method, objects, o) ->
        JpaFilterUtil.setLogicParam(entityManager));
  }
}
