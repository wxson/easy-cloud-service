package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.UserRoleDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GR
 * @date 2023/8/3 17:57
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleDO, Long> {

  /**
   * 根据用户ID删除数据
   *
   * @param userId 用户ID
   */
  void deleteByUserId(String userId);

  /**
   * 根据用户ID获取用户角色信息
   *
   * @param userId 用户ID
   * @return
   */
  List<UserRoleDO> findByUserId(String userId);

}
