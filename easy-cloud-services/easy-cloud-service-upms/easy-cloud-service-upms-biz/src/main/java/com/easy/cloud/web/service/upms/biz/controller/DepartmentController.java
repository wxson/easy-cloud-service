package com.easy.cloud.web.service.upms.biz.controller;

import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.upms.biz.domain.db.DepartmentDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.DepartmentDTO;
import com.easy.cloud.web.service.upms.biz.domain.query.DepartmentQuery;
import com.easy.cloud.web.service.upms.biz.service.IDepartmentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fast Java
 * @date 2021-04-06
 */
@Slf4j
@RestController
@RequestMapping("department")
@AllArgsConstructor
@Api(value = "部门管理", tags = "部门管理")
public class DepartmentController extends BaseController<DepartmentQuery, DepartmentDTO, DepartmentDO> {

    private final IDepartmentService departmentService;

    @Override
    public IRepositoryService<DepartmentDO> getService() {
        return departmentService;
    }
}