package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, String> {

  /**
   * 根据账号获取用户信息
   *
   * @param account 账号信息
   * @return
   */
  UserDO findByAccount(String account);

  /**
   * 匹配数据，任意一个条件符合即可
   *
   * @param unionId 唯一标识
   * @param appleId 唯一标识
   * @return
   */
  UserDO findByUnionIdOOrAppleId(String unionId, String appleId);
}