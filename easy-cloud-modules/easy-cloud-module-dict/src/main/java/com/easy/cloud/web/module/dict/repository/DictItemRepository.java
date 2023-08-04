package com.easy.cloud.web.module.dict.repository;

import com.easy.cloud.web.module.dict.domain.db.DictItemDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DictItem持久化
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Repository
public interface DictItemRepository extends JpaRepository<DictItemDO, Long> {

  /**
   * 根据字典ID获取字典数据
   *
   * @param dictId 字典ID
   * @return
   */
  List<DictItemDO> findByDictId(Long dictId);

  /**
   * 根据字典类型获取字典数据
   *
   * @param dictType 字典类型（如：login_type）
   * @return
   */
  List<DictItemDO> findByDictType(String dictType);
}