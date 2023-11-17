package com.easy.cloud.web.component.mysql.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

/**
 * Entity Listener
 *
 * @author GR
 * @date 2023/11/10 9:17
 */
@Slf4j
public class EntityListener {

  /**
   * 保存之前
   *
   * @param object 保存对象
   */
  @PrePersist
  public void prePersist(Object object) {
    if (object instanceof BaseEntity) {
      BaseEntity baseEntity = (BaseEntity) object;
      baseEntity.setCreateAt(DateUtil.date());
      try {
        // 非必须
        AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
        baseEntity.setCreateBy(authenticationUser.getId());
        // 如果初始租户为空，则设置租户信息
        if (StrUtil.isBlank(baseEntity.getTenantId())) {
          baseEntity.setTenantId(authenticationUser.getTenant());
        }
      } catch (Exception exception) {
      }
    }
  }

  /**
   * 保存之后
   *
   * @param object 保存对象
   */
  @PostPersist
  public void postPersist(Object object) {
  }

  /**
   * 更新之前之前
   *
   * @param object 保存对象
   */
  @PreUpdate
  public void preUpdate(Object object) {
    if (object instanceof BaseEntity) {
      BaseEntity baseEntity = (BaseEntity) object;
      baseEntity.setUpdateAt(DateUtil.date());
      try {
        // 非必须
        baseEntity.setUpdateBy(SecurityUtils.getAuthenticationUser().getId());
      } catch (Exception exception) {
      }
    }
  }

  /**
   * 更新之后
   *
   * @param object 保存对象
   */
  @PostUpdate
  public void postUpdate(Object object) {
  }
}
