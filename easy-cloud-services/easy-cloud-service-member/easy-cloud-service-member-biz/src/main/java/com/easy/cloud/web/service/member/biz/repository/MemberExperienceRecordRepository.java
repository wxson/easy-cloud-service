package com.easy.cloud.web.service.member.biz.repository;

import com.easy.cloud.web.service.member.biz.domain.MemberExperienceRecordDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberExperienceRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
@Repository
public interface MemberExperienceRecordRepository extends JpaRepository<MemberExperienceRecordDO, String> {

    /**
     * 根据用户ID获取用户经验记录数据
     *
     * @param userId
     * @return
     */
    List<MemberExperienceRecordDO> findAllByUserIdOrderByCreateAt(String userId);

    /**
     * 根据用户ID获取用户经验记录分页数据
     *
     * @param userId   用户ID
     * @param pageable 分页信息
     * @return
     */
    Page<MemberExperienceRecordDO> findAllByUserIdOrderByCreateAt(String userId, Pageable pageable);
}