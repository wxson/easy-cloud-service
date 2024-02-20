package com.easy.cloud.web.module.certification.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.PersonalAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.vo.PersonalAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.service.IPersonalAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * PersonalAuthentication API
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Slf4j
@RestController
@RequestMapping(value = "personal/authentication")
@Api(value = "PersonalAuthentication", tags = "个人认证管理")
public class PersonalAuthenticationController {

    @Autowired
    private IPersonalAuthenticationService personalAuthenticationService;

    /**
     * 新增
     *
     * @param personalAuthenticationDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "submit")
    @ApiOperation(value = "提交个人认证信息")
    public HttpResult<PersonalAuthenticationVO> submit(@Validated @RequestBody PersonalAuthenticationDTO personalAuthenticationDTO) {
        return HttpResult.ok(personalAuthenticationService.submit(personalAuthenticationDTO));
    }

    /**
     * 更新
     *
     * @param personalAuthenticationDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新个人认证信息")
    public HttpResult<PersonalAuthenticationVO> update(@Validated @RequestBody PersonalAuthenticationDTO personalAuthenticationDTO) {
        return HttpResult.ok(personalAuthenticationService.update(personalAuthenticationDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param personalAuthenticationId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{personalAuthenticationId}")
    @ApiOperation(value = "根据ID移除个人认证信息")
    public HttpResult<Boolean> removeById(@PathVariable @NotNull(message = "当前ID不能为空") String personalAuthenticationId) {
        return HttpResult.ok(personalAuthenticationService.removeById(personalAuthenticationId));
    }

    /**
     * 根据ID获取详情
     *
     * @param personalAuthenticationId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{personalAuthenticationId}")
    @ApiOperation(value = "根据ID获取个人认证信息")
    public HttpResult<PersonalAuthenticationVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String personalAuthenticationId) {
        return HttpResult.ok(personalAuthenticationService.detailById(personalAuthenticationId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取个人认证信息列表")
    public HttpResult<List<PersonalAuthenticationVO>> list() {
        return HttpResult.ok(personalAuthenticationService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取个人认证信息分页列表")
    public HttpResult<Page<PersonalAuthenticationVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(personalAuthenticationService.page(page, size));
    }

    /**
     * 审核通过
     *
     * @param auditDTO 审核参数
     * @return
     */
    @GetMapping(value = "audit/success")
    @ApiOperation(value = "审核通过")
    public HttpResult<PersonalAuthenticationVO> authenticationSuccess(@RequestBody AuditDTO auditDTO) {
        auditDTO.setStatus(AuthenticationStatusEnum.SUCCESS);
        return HttpResult.ok(personalAuthenticationService.updateAuthenticationStatus(auditDTO));
    }

    /**
     * 审核失败
     *
     * @param auditDTO 审核参数
     * @return
     */
    @GetMapping(value = "audit/fail")
    @ApiOperation(value = "审核失败")
    public HttpResult<PersonalAuthenticationVO> authenticationFail(@RequestBody AuditDTO auditDTO) {
        auditDTO.setStatus(AuthenticationStatusEnum.FAIL);
        return HttpResult.ok(personalAuthenticationService.updateAuthenticationStatus(auditDTO));
    }
}