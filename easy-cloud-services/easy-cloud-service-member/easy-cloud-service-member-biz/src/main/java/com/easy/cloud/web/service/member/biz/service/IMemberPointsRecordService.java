package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.service.member.api.dto.MemberPointsRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * MemberPointsRecord interface
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
public interface IMemberPointsRecordService {

    /**
     * 新增数据
     *
     * @param memberPointsRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO
     */
    MemberPointsRecordVO save(MemberPointsRecordDTO memberPointsRecordDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param memberPointsRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO
     */
    MemberPointsRecordVO update(MemberPointsRecordDTO memberPointsRecordDTO);

    /**
     * 根据ID删除数据
     *
     * @param memberPointsRecordId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String memberPointsRecordId);

    /**
     * 根据ID获取详情
     *
     * @param memberPointsRecordId 对象ID
     * @return java.lang.Boolean
     */
    MemberPointsRecordVO detailById(String memberPointsRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @param userId 查询用户ID
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO> 返回列表数据
     */
    List<MemberPointsRecordVO> list(String userId);

    /**
     * 根据条件获取分页数据
     *
     * @param userId 查询用户ID
     * @param page   当前页
     * @param size   每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO> 返回列表数据
     */
    Page<MemberPointsRecordVO> page(String userId, int page, int size);
}