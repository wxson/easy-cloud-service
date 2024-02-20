package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Member interface
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
public interface IMemberService {

    /**
     * 新增数据
     *
     * @param memberDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberVO
     */
    MemberVO save(MemberDTO memberDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param memberDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.MemberVO
     */
    MemberVO update(MemberDTO memberDTO);

    /**
     * 根据ID删除数据
     *
     * @param memberId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String memberId);

    /**
     * 根据ID获取详情
     *
     * @param memberId 对象ID
     * @return java.lang.Boolean
     */
    MemberVO detailById(String memberId);

    /**
     * 根据用户ID获取会员信息
     *
     * @param userId
     * @return
     */
    MemberVO detailByUserId(String userId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberVO> 返回列表数据
     */
    List<MemberVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.member.api.vo.MemberVO> 返回列表数据
     */
    Page<MemberVO> page(int page, int size);

    /**
     * 初始化会员信息
     *
     * @return
     */
    MemberVO createMember();
}