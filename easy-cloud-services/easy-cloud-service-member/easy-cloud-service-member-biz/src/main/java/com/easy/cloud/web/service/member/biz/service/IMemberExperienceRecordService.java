package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.service.member.api.dto.MemberExperienceRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * MemberExperienceRecord interface
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
public interface IMemberExperienceRecordService {

    /**
     * 新增数据
     *
     * @param memberExperienceRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO
     */
    MemberExperienceRecordVO save(MemberExperienceRecordDTO memberExperienceRecordDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param memberExperienceRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO
     */
    MemberExperienceRecordVO update(MemberExperienceRecordDTO memberExperienceRecordDTO);

    /**
     * 根据ID删除数据
     *
     * @param memberExperienceRecordId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String memberExperienceRecordId);

    /**
     * 根据ID获取详情
     *
     * @param memberExperienceRecordId 对象ID
     * @return java.lang.Boolean
     */
    MemberExperienceRecordVO detailById(String memberExperienceRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @param userId 用户ID
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO> 返回列表数据
     */
    List<MemberExperienceRecordVO> list(String userId);

    /**
     * 根据条件获取分页数据
     *
     * @param userId 用户ID
     * @param page   当前页
     * @param size   每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO> 返回列表数据
     */
    Page<MemberExperienceRecordVO> page(String userId, int page, int size);
}