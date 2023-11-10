package com.easy.cloud.web.component.mysql.repository;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA 逻辑删除业务场景
 *
 * @author GR
 * @date 2023/10/27 9:47
 */
@NoRepositoryBean
public interface JpaLogicRepository<T extends BaseEntity, ID extends Serializable> extends
    JpaRepository<T, ID> {

  /**
   * 根据ID进行逻辑删除
   *
   * @param id 文档ID
   */
  @Query("UPDATE #{#entityName} table SET table.deleted = 'DELETED' WHERE table.id = ?1")
  @Transactional
  @Modifying
  void logicDelete(ID id);

  /**
   * 根据实体对象进行逻辑删除
   *
   * @param entity 实体对象
   */
  @Transactional
  default void logicDelete(T entity) {
    logicDelete((ID) entity.getId());
  }

  /**
   * 根据集合进行逻辑删除
   *
   * @param entities
   */
  @Transactional(rollbackFor = Exception.class)
  default void logicDelete(Iterable<? extends T> entities) {
    entities.forEach(entity -> logicDelete((ID) entity.getId()));
  }

  /**
   * 表所有数据进行逻辑删除
   */
  @Modifying
  @Transactional
  @Query("UPDATE #{#entityName} table SET table.deleted = 'DELETED' ")
  void logicDeleteAll();
}
