package com.easy.cloud.web.service.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.biz.domain.db.DepartmentDO;
import com.easy.cloud.web.service.upms.biz.mapper.DepartmentMapper;
import com.easy.cloud.web.service.upms.biz.service.IDepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Department 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-04-06
 */
@Slf4j
@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentDO> implements IDepartmentService {

    @Override
    public DepartmentDO verifyBeforeSave(DepartmentDO departmentDO) {
        // 部门校验规则：部门名称、父级部门ID，租户ID
        DepartmentDO existDepartmentDO = this.getOne(Wrappers.<DepartmentDO>lambdaQuery()
                .eq(DepartmentDO::getParentId, departmentDO.getParentId())
                .eq(DepartmentDO::getTenantId, departmentDO.getTenantId())
                .eq(DepartmentDO::getName, departmentDO.getName()));
        if (null != existDepartmentDO) {
            throw new BusinessException("当前部门名称已存在");
        }

        return departmentDO;
    }
}