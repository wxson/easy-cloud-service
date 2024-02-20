package com.easy.cloud.web.service.member.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberPointsRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO;
import com.easy.cloud.web.service.member.biz.domain.MemberPointsRecordDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MemberPointsRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
public class MemberPointsRecordConverter {

    /**
     * DTO转为DO
     *
     * @param memberPointsRecord 转换数据
     * @return com.easy.cloud.web.service.member.biz.domain.MemberPointsRecordDO
     */
    public static MemberPointsRecordDO convertTo(MemberPointsRecordDTO memberPointsRecord) {
        MemberPointsRecordDO memberPointsRecordDO = MemberPointsRecordDO.builder().build();
        BeanUtils.copyProperties(memberPointsRecord, memberPointsRecordDO, true);
        return memberPointsRecordDO;
    }

    /**
     * DO转为VO
     *
     * @param memberPointsRecord 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO
     */
    public static MemberPointsRecordVO convertTo(MemberPointsRecordDO memberPointsRecord) {
        MemberPointsRecordVO memberPointsRecordVO = MemberPointsRecordVO.builder().build();
        BeanUtils.copyProperties(memberPointsRecord, memberPointsRecordVO, true);
        return memberPointsRecordVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param memberPointsRecords 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO
     */
    public static List<MemberPointsRecordVO> convertTo(List<MemberPointsRecordDO> memberPointsRecords) {
        return memberPointsRecords.stream()
                .map(MemberPointsRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO
     */
    public static Page<MemberPointsRecordVO> convertTo(Page<MemberPointsRecordDO> page) {
        return page.map(MemberPointsRecordConverter::convertTo);
    }
}