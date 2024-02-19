package com.easy.cloud.web.module.certification.biz.controller;

import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * AuthenticationRecord API
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
@Slf4j
@RestController
@RequestMapping(value = "authenticationRecord")
public class AuthenticationRecordController {

    @Autowired
    private IAuthenticationRecordService authenticationRecordService;

    /**
     * 根据ID获取详情
     *
     * @param authenticationRecordId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{authenticationRecordId}")
    public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long authenticationRecordId) {
        return authenticationRecordService.detailById(authenticationRecordId);
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    public Object list() {
        return authenticationRecordService.list();
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    public Object page(@RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int size) {
        return authenticationRecordService.page(page, size);
    }
}