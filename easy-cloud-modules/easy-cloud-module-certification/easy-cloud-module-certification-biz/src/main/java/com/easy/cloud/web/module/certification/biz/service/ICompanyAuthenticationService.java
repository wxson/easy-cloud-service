package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.CompanyAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * CompanyAuthentication interface
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
public interface ICompanyAuthenticationService {

    /**
     * 新增数据
     *
     * @param companyAuthenticationDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    CompanyAuthenticationVO submit(CompanyAuthenticationDTO companyAuthenticationDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param companyAuthenticationDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    CompanyAuthenticationVO update(CompanyAuthenticationDTO companyAuthenticationDTO);

    /**
     * 更新审核状态数据
     *
     * @param auditDTO 审核参数
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    CompanyAuthenticationVO updateAuthenticationStatus(AuditDTO auditDTO);

    /**
     * 根据ID删除数据
     *
     * @param companyAuthenticationId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String companyAuthenticationId);

    /**
     * 根据ID获取详情
     *
     * @param companyAuthenticationId 对象ID
     * @return java.lang.Boolean
     */
    CompanyAuthenticationVO detailById(String companyAuthenticationId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO> 返回列表数据
     */
    List<CompanyAuthenticationVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO> 返回列表数据
     */
    Page<CompanyAuthenticationVO> page(int page, int size);
}