package com.easy.cloud.web.component.mysql.utils;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.mysql.constants.MysqlConstant;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import javax.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;

/**
 * JPA 过滤相关工具
 *
 * @author GR
 * @date 2023/11/10 13:59
 */
@Slf4j
@UtilityClass
public class JpaFilterUtil {

  /**
   * 设置租户参数
   *
   * @param entityManager 管理器
   */
  public void setTenantParam(EntityManager entityManager) {
    try {
      AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
      if (StrUtil.isNotBlank(authenticationUser.getTenant())) {
        Session session = entityManager.unwrap(Session.class);
        Filter tenant = session.enableFilter(MysqlConstant.TENANT_FILTER_NAME);
        tenant.setParameter(MysqlConstant.TENANT_ID, authenticationUser.getTenant());
      }
    } catch (Exception exception) {

    }
  }

  /**
   * 设置逻辑删除参数
   *
   * @param entityManager 管理器
   */
  public void setLogicParam(EntityManager entityManager) {
    Session session = entityManager.unwrap(Session.class);
    Filter tenant = session.enableFilter(MysqlConstant.LOGIC_FILTER_NAME);
    tenant.setParameter(MysqlConstant.LOGIC_DELETED, DeletedEnum.UN_DELETED.name());
  }

}
