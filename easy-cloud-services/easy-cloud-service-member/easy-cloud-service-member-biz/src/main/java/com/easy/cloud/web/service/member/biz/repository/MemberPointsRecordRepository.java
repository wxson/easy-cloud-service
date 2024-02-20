package com.easy.cloud.web.service.member.biz.repository;

import com.easy.cloud.web.service.member.biz.domain.MemberLevelRecordDO;
import com.easy.cloud.web.service.member.biz.domain.MemberPointsRecordDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberPointsRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
@Repository
public interface MemberPointsRecordRepository extends JpaRepository<MemberPointsRecordDO, String> {

    /**
     * 根据用户ID获取用户积分记录数据
     *
     * @param userId
     * @return
     */
    List<MemberPointsRecordDO> findAllByUserIdOrderByCreateAt(String userId);

    /**
     * 根据用户ID获取用户积分记录分页数据
     *
     * @param userId   用户ID
     * @param pageable 分页信息
     * @return
     */
    Page<MemberPointsRecordDO> findAllByUserIdOrderByCreateAt(String userId, Pageable pageable);
}