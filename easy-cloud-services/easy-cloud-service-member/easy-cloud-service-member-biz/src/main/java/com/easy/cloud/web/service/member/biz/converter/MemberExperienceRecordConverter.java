package com.easy.cloud.web.service.member.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberExperienceRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO;
import com.easy.cloud.web.service.member.biz.domain.MemberExperienceRecordDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MemberExperienceRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
public class MemberExperienceRecordConverter {

    /**
     * DTO转为DO
     *
     * @param memberExperienceRecord 转换数据
     * @return com.easy.cloud.web.service.member.biz.domain.MemberExperienceRecordDO
     */
    public static MemberExperienceRecordDO convertTo(MemberExperienceRecordDTO memberExperienceRecord) {
        MemberExperienceRecordDO memberExperienceRecordDO = MemberExperienceRecordDO.builder().build();
        BeanUtils.copyProperties(memberExperienceRecord, memberExperienceRecordDO, true);
        return memberExperienceRecordDO;
    }

    /**
     * DO转为VO
     *
     * @param memberExperienceRecord 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO
     */
    public static MemberExperienceRecordVO convertTo(MemberExperienceRecordDO memberExperienceRecord) {
        MemberExperienceRecordVO memberExperienceRecordVO = MemberExperienceRecordVO.builder().build();
        BeanUtils.copyProperties(memberExperienceRecord, memberExperienceRecordVO, true);
        return memberExperienceRecordVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param memberExperienceRecords 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO
     */
    public static List<MemberExperienceRecordVO> convertTo(List<MemberExperienceRecordDO> memberExperienceRecords) {
        return memberExperienceRecords.stream()
                .map(MemberExperienceRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO
     */
    public static Page<MemberExperienceRecordVO> convertTo(Page<MemberExperienceRecordDO> page) {
        return page.map(MemberExperienceRecordConverter::convertTo);
    }
}