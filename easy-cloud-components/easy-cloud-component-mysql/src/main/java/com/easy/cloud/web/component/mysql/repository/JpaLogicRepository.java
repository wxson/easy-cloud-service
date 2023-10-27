package com.easy.cloud.web.component.mysql.repository;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.deleted = 'UN_DELETED'")
  List<T> findAll();

  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.id in ?1 AND table.deleted = 'UN_DELETED'")
  Iterable<T> findAll(Iterable<ID> ids);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.deleted = 'UN_DELETED'")
  Page<T> findAll(Pageable pageable);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.deleted = 'UN_DELETED' AND table.id in (?1)")
  List<T> findAllById(Iterable<ID> ids);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.id = ?1 AND table.deleted = 'UN_DELETED'")
  Optional<T> findById(ID id);

  @Transactional(readOnly = true)
  @Query("SELECT table FROM #{#entityName} table WHERE table.id = ?1 AND table.deleted = 'UN_DELETED'")
  T findOne(ID id);

  @Override
  @Transactional(readOnly = true)
  @Query("SELECT count(table) FROM #{#entityName} table WHERE table.deleted = 'UN_DELETED'")
  long count();

  @Transactional(readOnly = true)
  default boolean exists(ID id) {
    return findOne(id) != null;
  }

  @Query("UPDATE #{#entityName} table SET table.deleted = 'DELETED' WHERE table.id = ?1")
  @Transactional
  @Modifying
  void logicDelete(ID id);

  @Transactional
  default void logicDelete(T entity) {
    logicDelete((ID) entity.getId());
  }

  @Transactional(rollbackFor = Exception.class)
  default void logicDelete(Iterable<? extends T> entities) {
    entities.forEach(entity -> logicDelete((ID) entity.getId()));
  }

  @Query("UPDATE #{#entityName} table SET table.deleted = 'DELETED' ")
  @Transactional
  @Modifying
  void logicDeleteAll();
}
