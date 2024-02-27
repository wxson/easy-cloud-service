package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.DeptDO;
import org.springframework.stereotype.Repository;

/**
 * Department持久化
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Repository
public interface DeptRepository extends JpaLogicRepository<DeptDO, String> {

    /**
     * 统计当前部门下是否存在子部门
     *
     * @param parentId 父级部门
     * @return
     */
    long countByParentId(String parentId);
}