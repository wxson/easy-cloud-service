package com.easy.cloud.web.service.member.biz.repository;

import com.easy.cloud.web.service.member.biz.domain.MemberLevelDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MemberLevel持久化
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
@Repository
public interface MemberLevelRepository extends JpaRepository<MemberLevelDO, String> {

}