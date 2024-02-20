package com.easy.cloud.web.service.member.biz.repository;

import com.easy.cloud.web.service.member.biz.domain.MemberLevelRecordDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberLevelRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
@Repository
public interface MemberLevelRecordRepository extends JpaRepository<MemberLevelRecordDO, String> {

    /**
     * 根据用户ID获取用户等级记录数据
     *
     * @param userId
     * @return
     */
    List<MemberLevelRecordDO> findAllByUserIdOrderByCreateAt(String userId);

    /**
     * 根据用户ID获取用户等级记录分页数据
     *
     * @param userId   用户ID
     * @param pageable 分页信息
     * @return
     */
    Page<MemberLevelRecordDO> findAllByUserIdOrderByCreateAt(String userId, Pageable pageable);
}