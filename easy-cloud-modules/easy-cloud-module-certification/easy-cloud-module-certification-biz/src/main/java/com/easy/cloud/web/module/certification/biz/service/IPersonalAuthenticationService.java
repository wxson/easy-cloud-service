package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.PersonalAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import com.easy.cloud.web.module.certification.api.vo.PersonalAuthenticationVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * PersonalAuthentication interface
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
public interface IPersonalAuthenticationService {

    /**
     * 新增数据
     *
     * @param personalAuthenticationDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    PersonalAuthenticationVO submit(PersonalAuthenticationDTO personalAuthenticationDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param personalAuthenticationDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    PersonalAuthenticationVO update(PersonalAuthenticationDTO personalAuthenticationDTO);

    /**
     * 更新审核状态数据
     *
     * @param auditDTO 审核参数
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    PersonalAuthenticationVO updateAuthenticationStatus(AuditDTO auditDTO);

    /**
     * 根据ID删除数据
     *
     * @param personalAuthenticationId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String personalAuthenticationId);

    /**
     * 根据ID获取详情
     *
     * @param personalAuthenticationId 对象ID
     * @return java.lang.Boolean
     */
    PersonalAuthenticationVO detailById(String personalAuthenticationId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO> 返回列表数据
     */
    List<PersonalAuthenticationVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO> 返回列表数据
     */
    Page<PersonalAuthenticationVO> page(int page, int size);
}