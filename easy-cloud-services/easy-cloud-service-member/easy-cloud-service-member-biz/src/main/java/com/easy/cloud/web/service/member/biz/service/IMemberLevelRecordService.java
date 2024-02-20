package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.service.member.api.dto.MemberLevelRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * MemberLevelRecord interface
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
public interface IMemberLevelRecordService {

    /**
     * 新增数据
     *
     * @param memberLevelRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO
     */
    MemberLevelRecordVO save(MemberLevelRecordDTO memberLevelRecordDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param memberLevelRecordDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO
     */
    MemberLevelRecordVO update(MemberLevelRecordDTO memberLevelRecordDTO);

    /**
     * 根据ID删除数据
     *
     * @param memberLevelRecordId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String memberLevelRecordId);

    /**
     * 根据ID获取详情
     *
     * @param memberLevelRecordId 对象ID
     * @return java.lang.Boolean
     */
    MemberLevelRecordVO detailById(String memberLevelRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @param userId 用户ID
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO> 返回列表数据
     */
    List<MemberLevelRecordVO> list(String userId);

    /**
     * 根据条件获取分页数据
     *
     * @param userId 用户ID
     * @param page   当前页
     * @param size   每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO> 返回列表数据
     */
    Page<MemberLevelRecordVO> page(String userId, int page, int size);
}