package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * 登录User持久化，避免收租户影响
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@EnableLogic
@Repository
public interface LoginUserRepository extends JpaLogicRepository<UserDO, String> {

  /**
   * 根据账号获取用户信息
   *
   * @param userName 账号信息
   * @return
   */
  Optional<UserDO> findByUserName(String userName);
}