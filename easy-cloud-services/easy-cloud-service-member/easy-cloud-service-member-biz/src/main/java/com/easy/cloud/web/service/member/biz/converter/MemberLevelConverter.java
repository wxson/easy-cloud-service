package com.easy.cloud.web.service.member.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberLevelDTO;
import com.easy.cloud.web.service.member.biz.domain.CompanyAuthenticationDO;
import com.easy.cloud.web.service.member.biz.domain.MemberLevelDO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
/**
 * MemberLevel转换器
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
public class MemberLevelConverter {

    /**
     * DTO转为DO
     *
     * @param memberLevel 转换数据
     * @return com.easy.cloud.web.service.member.biz.domain.MemberLevelDO
     */
    public static MemberLevelDO convertTo(MemberLevelDTO memberLevel){
        MemberLevelDO memberLevelDO = MemberLevelDO.builder().build();
        BeanUtils.copyProperties(memberLevel, memberLevelDO, true);
        return memberLevelDO;
    }

    /**
     * DO转为VO
     *
     * @param memberLevel 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelVO
     */
    public static MemberLevelVO convertTo(MemberLevelDO memberLevel){
        MemberLevelVO memberLevelVO = MemberLevelVO.builder().build();
        BeanUtils.copyProperties(memberLevel, memberLevelVO, true);
        return memberLevelVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param memberLevels 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelVO
     */
    public static List<MemberLevelVO> convertTo(List<MemberLevelDO> memberLevels){
        return memberLevels.stream()
                .map(MemberLevelConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelVO
     */
    public static Page<MemberLevelVO> convertTo(Page<MemberLevelDO> page){
        return page.map(MemberLevelConverter::convertTo);
    }
}