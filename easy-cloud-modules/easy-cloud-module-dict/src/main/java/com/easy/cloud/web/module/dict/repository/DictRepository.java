package com.easy.cloud.web.module.dict.repository;

import com.easy.cloud.web.module.dict.domain.db.DictDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dict持久化
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Repository
public interface DictRepository extends JpaRepository<DictDO, Long> {


  /**
   * 根据字典类型获取字典数据
   *
   * @param type 字典类型
   * @return
   */
  DictDO findByType(String type);
}