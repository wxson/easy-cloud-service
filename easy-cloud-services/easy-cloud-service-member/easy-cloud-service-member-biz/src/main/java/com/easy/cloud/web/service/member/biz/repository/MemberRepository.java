package com.easy.cloud.web.service.member.biz.repository;

import com.easy.cloud.web.service.member.biz.domain.MemberDO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Member持久化
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberDO, String> {

  /**
   * 根据用户ID获取会员详情
   *
   * @param userId 用户ID
   * @return
   */
  Optional<MemberDO> findByUserId(String userId);

}