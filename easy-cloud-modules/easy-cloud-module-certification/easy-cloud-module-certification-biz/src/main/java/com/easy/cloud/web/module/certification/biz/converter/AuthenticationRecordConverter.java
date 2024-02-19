package com.easy.cloud.web.module.certification.biz.converter;

import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.biz.domain.AuthenticationRecordDO;
import com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
/**
 * AuthenticationRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
public class AuthenticationRecordConverter {

    /**
     * DTO转为DO
     *
     * @param authenticationRecord 转换数据
     * @return com.easy.cloud.web.module.certification.biz.domain.AuthenticationRecordDO
     */
    public static AuthenticationRecordDO convertTo(AuthenticationRecordDTO authenticationRecord){
        return AuthenticationRecordDO.builder()
                .id(authenticationRecord.getId())
                .authenticationId(authenticationRecord.getAuthenticationId())
                .remark(authenticationRecord.getRemark())
                .type(authenticationRecord.getType())
                .status(authenticationRecord.getStatus())
                .build();
    }

    /**
     * DO转为VO
     *
     * @param authenticationRecord 转换数据
     * @return com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO
     */
    public static AuthenticationRecordVO convertTo(AuthenticationRecordDO authenticationRecord){
        return AuthenticationRecordVO.builder()
                .id(authenticationRecord.getId())
                .creatorAt(authenticationRecord.getCreatorAt())
                .createAt(authenticationRecord.getCreateAt())
                .updateAt(authenticationRecord.getUpdateAt())
                .authenticationId(authenticationRecord.getAuthenticationId())
                .remark(authenticationRecord.getRemark())
                .type(authenticationRecord.getType())
                .status(authenticationRecord.getStatus())
                .build();
    }

    /**
     * 列表DO转为VO
     *
     * @param authenticationRecords 转换数据
     * @return com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO
     */
    public static List<AuthenticationRecordVO> convertTo(List<AuthenticationRecordDO> authenticationRecords){
        return authenticationRecords.stream()
                .map(AuthenticationRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO
     */
    public static Page<AuthenticationRecordVO> convertTo(Page<AuthenticationRecordDO> page){
        return page.map(AuthenticationRecordConverter::convertTo);
    }
}