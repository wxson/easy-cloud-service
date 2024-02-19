package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * AuthenticationRecord interface
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
public interface IAuthenticationRecordService {

    /**
     * 新增数据
     *
     * @param authenticationRecordDTO 保存参数
     * @return com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO
     */
    AuthenticationRecordVO save(AuthenticationRecordDTO authenticationRecordDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param authenticationRecordDTO 保存参数
     * @return com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO
     */
    AuthenticationRecordVO update(AuthenticationRecordDTO authenticationRecordDTO);

    /**
     * 根据ID删除数据
     *
     * @param authenticationRecordId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String authenticationRecordId);

    /**
     * 根据ID获取详情
     *
     * @param authenticationRecordId 对象ID
     * @return java.lang.Boolean
     */
    AuthenticationRecordVO detailById(String authenticationRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO> 返回列表数据
     */
    List<AuthenticationRecordVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO> 返回列表数据
     */
    Page<AuthenticationRecordVO> page(int page, int size);
}