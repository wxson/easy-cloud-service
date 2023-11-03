package com.easy.cloud.web.module.dict.biz.repository;

import com.easy.cloud.web.module.dict.biz.domain.DictItemDO;
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
public interface DictItemRepository extends JpaRepository<DictItemDO, String> {

  /**
   * 根据字典类型获取字典数据
   *
   * @param dictType 字典类型（如：login_type）
   * @return
   */
  List<DictItemDO> findByDictType(String dictType);

  /**
   * 根据字典类型获取字典数据
   *
   * @param dictTypes 字典类型（如：login_type）
   * @return
   */
  List<DictItemDO> findByDictTypeIn(List<String> dictTypes);
}