package com.easy.cloud.web.module.certification.biz.controller;

import cn.hutool.core.util.PhoneUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.CompanyAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.service.ICompanyAuthenticationService;
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
 * CompanyAuthentication API
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Slf4j
@RestController
@RequestMapping(value = "company/authentication")
@Api(value = "CompanyAuthentication", tags = "企业认证管理")
public class CompanyAuthenticationController {

    @Autowired
    private ICompanyAuthenticationService companyAuthenticationService;

    /**
     * 新增
     *
     * @param companyAuthenticationDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "submit")
    @ApiOperation(value = "提交企业认证信息信息")
    public HttpResult<CompanyAuthenticationVO> submit(@Validated @RequestBody CompanyAuthenticationDTO companyAuthenticationDTO) {
        return HttpResult.ok(companyAuthenticationService.submit(companyAuthenticationDTO));
    }

    /**
     * 更新
     *
     * @param companyAuthenticationDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新企业认证信息信息")
    public HttpResult<CompanyAuthenticationVO> update(@Validated @RequestBody CompanyAuthenticationDTO companyAuthenticationDTO) {
        return HttpResult.ok(companyAuthenticationService.update(companyAuthenticationDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param companyAuthenticationId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{companyAuthenticationId}")
    @ApiOperation(value = "根据ID移除企业认证信息信息")
    public HttpResult<Boolean> removeById(@PathVariable @NotNull(message = "当前ID不能为空") String companyAuthenticationId) {
        return HttpResult.ok(companyAuthenticationService.removeById(companyAuthenticationId));
    }

    /**
     * 根据ID获取详情
     *
     * @param companyAuthenticationId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{companyAuthenticationId}")
    @ApiOperation(value = "根据ID获取企业认证信息信息")
    public HttpResult<CompanyAuthenticationVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String companyAuthenticationId) {
        return HttpResult.ok(companyAuthenticationService.detailById(companyAuthenticationId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取企业认证信息信息列表")
    public HttpResult<List<CompanyAuthenticationVO>> list() {
        return HttpResult.ok(companyAuthenticationService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取企业认证信息信息分页列表")
    public HttpResult<Page<CompanyAuthenticationVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(companyAuthenticationService.page(page, size));
    }

    /**
     * 审核通过
     *
     * @param auditDTO 审核参数
     * @return
     */
    @GetMapping(value = "audit/success")
    @ApiOperation(value = "审核通过")
    public HttpResult<CompanyAuthenticationVO> authenticationSuccess(@RequestBody AuditDTO auditDTO) {
        auditDTO.setStatus(AuthenticationStatusEnum.SUCCESS);
        return HttpResult.ok(companyAuthenticationService.updateAuthenticationStatus(auditDTO));
    }

    /**
     * 审核失败
     *
     * @param auditDTO 审核参数
     * @return
     */
    @GetMapping(value = "audit/fail")
    @ApiOperation(value = "审核失败")
    public HttpResult<CompanyAuthenticationVO> authenticationFail(@RequestBody AuditDTO auditDTO) {
        auditDTO.setStatus(AuthenticationStatusEnum.FAIL);
        return HttpResult.ok(companyAuthenticationService.updateAuthenticationStatus(auditDTO));
    }
}