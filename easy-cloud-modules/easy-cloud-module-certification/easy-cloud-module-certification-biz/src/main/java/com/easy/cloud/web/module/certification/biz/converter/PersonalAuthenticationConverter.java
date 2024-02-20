package com.easy.cloud.web.module.certification.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.certification.api.dto.PersonalAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.PersonalAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.domain.PersonalAuthenticationDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PersonalAuthentication转换器
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
public class PersonalAuthenticationConverter {

    /**
     * DTO转为DO
     *
     * @param personalAuthentication 转换数据
     * @return com.easy.cloud.web.module.certification.biz.domain.PersonalAuthenticationDO
     */
    public static PersonalAuthenticationDO convertTo(PersonalAuthenticationDTO personalAuthentication) {
        PersonalAuthenticationDO personalAuthenticationDO = PersonalAuthenticationDO.builder().build();
        BeanUtils.copyProperties(personalAuthentication, personalAuthenticationDO, true);
        return personalAuthenticationDO;
    }

    /**
     * DO转为VO
     *
     * @param personalAuthentication 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    public static PersonalAuthenticationVO convertTo(PersonalAuthenticationDO personalAuthentication) {
        PersonalAuthenticationVO personalAuthenticationVO = PersonalAuthenticationVO.builder().build();
        BeanUtils.copyProperties(personalAuthentication, personalAuthenticationVO, true);
        return personalAuthenticationVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param personalAuthentications 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    public static List<PersonalAuthenticationVO> convertTo(List<PersonalAuthenticationDO> personalAuthentications) {
        return personalAuthentications.stream()
                .map(PersonalAuthenticationConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.PersonalAuthenticationVO
     */
    public static Page<PersonalAuthenticationVO> convertTo(Page<PersonalAuthenticationDO> page) {
        return page.map(PersonalAuthenticationConverter::convertTo);
    }
}