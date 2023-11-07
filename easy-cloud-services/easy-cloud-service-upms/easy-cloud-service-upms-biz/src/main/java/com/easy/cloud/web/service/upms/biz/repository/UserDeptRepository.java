package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.UserDeptDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GR
 * @date 2023/8/3 17:57
 */
@Repository
public interface UserDeptRepository extends JpaRepository<UserDeptDO, String> {

  /**
   * 根据用户ID删除数据
   *
   * @param userId 用户ID
   */
  void deleteByUserId(String userId);

  /**
   * 根据用户ID获取用户部门信息
   *
   * @param userId 用户ID
   * @return
   */
  List<UserDeptDO> findByUserId(String userId);

  /**
   * 根据用户ID获取用户部门信息
   *
   * @param userIds 用户ID集合
   * @return
   */
  List<UserDeptDO> findAllByUserIdIn(List<String> userIds);
}
