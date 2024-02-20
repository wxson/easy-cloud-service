package com.easy.cloud.web.service.member.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberLevelRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO;
import com.easy.cloud.web.service.member.biz.domain.MemberLevelRecordDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MemberLevelRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
public class MemberLevelRecordConverter {

    /**
     * DTO转为DO
     *
     * @param memberLevelRecord 转换数据
     * @return com.easy.cloud.web.service.member.biz.domain.MemberLevelRecordDO
     */
    public static MemberLevelRecordDO convertTo(MemberLevelRecordDTO memberLevelRecord) {
        MemberLevelRecordDO memberLevelRecordDO = MemberLevelRecordDO.builder().build();
        BeanUtils.copyProperties(memberLevelRecord, memberLevelRecordDO, true);
        return memberLevelRecordDO;
    }

    /**
     * DO转为VO
     *
     * @param memberLevelRecord 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO
     */
    public static MemberLevelRecordVO convertTo(MemberLevelRecordDO memberLevelRecord) {
        MemberLevelRecordVO memberLevelRecordVO = MemberLevelRecordVO.builder().build();
        BeanUtils.copyProperties(memberLevelRecord, memberLevelRecordVO, true);
        return memberLevelRecordVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param memberLevelRecords 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO
     */
    public static List<MemberLevelRecordVO> convertTo(List<MemberLevelRecordDO> memberLevelRecords) {
        return memberLevelRecords.stream()
                .map(MemberLevelRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO
     */
    public static Page<MemberLevelRecordVO> convertTo(Page<MemberLevelRecordDO> page) {
        return page.map(MemberLevelRecordConverter::convertTo);
    }
}