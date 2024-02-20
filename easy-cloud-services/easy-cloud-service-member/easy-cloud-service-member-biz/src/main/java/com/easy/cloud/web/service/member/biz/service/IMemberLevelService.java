package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.service.member.api.dto.MemberLevelDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * MemberLevel interface
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
public interface IMemberLevelService {

    /**
     * 新增数据
     *
     * @param memberLevelDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelVO
     */
    MemberLevelVO save(MemberLevelDTO memberLevelDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param memberLevelDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelVO
     */
    MemberLevelVO update(MemberLevelDTO memberLevelDTO);

    /**
     * 根据ID删除数据
     *
     * @param memberLevelId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String memberLevelId);

    /**
     * 根据ID获取详情
     *
     * @param memberLevelId 对象ID
     * @return java.lang.Boolean
     */
    MemberLevelVO detailById(String memberLevelId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberLevelVO> 返回列表数据
     */
    List<MemberLevelVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberLevelVO> 返回列表数据
     */
    Page<MemberLevelVO> page(int page, int size);
}