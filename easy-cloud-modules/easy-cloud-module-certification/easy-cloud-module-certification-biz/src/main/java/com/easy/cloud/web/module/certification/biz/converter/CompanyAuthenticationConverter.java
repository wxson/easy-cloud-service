package com.easy.cloud.web.module.certification.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO;
import com.easy.cloud.web.module.certification.api.dto.CompanyAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CompanyAuthentication转换器
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
public class CompanyAuthenticationConverter {

    /**
     * DTO转为DO
     *
     * @param companyAuthentication 转换数据
     * @return com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO
     */
    public static CompanyAuthenticationDO convertTo(CompanyAuthenticationDTO companyAuthentication) {
        CompanyAuthenticationDO companyAuthenticationDO = CompanyAuthenticationDO.builder().build();
        BeanUtils.copyProperties(companyAuthentication, companyAuthenticationDO, true);
        return companyAuthenticationDO;
    }

    /**
     * DO转为VO
     *
     * @param companyAuthentication 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    public static CompanyAuthenticationVO convertTo(CompanyAuthenticationDO companyAuthentication) {
        CompanyAuthenticationVO companyAuthenticationVO = CompanyAuthenticationVO.builder().build();
        BeanUtils.copyProperties(companyAuthentication, companyAuthenticationVO, true);
        return companyAuthenticationVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param companyAuthentications 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    public static List<CompanyAuthenticationVO> convertTo(List<CompanyAuthenticationDO> companyAuthentications) {
        return companyAuthentications.stream()
                .map(CompanyAuthenticationConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.CompanyAuthenticationVO
     */
    public static Page<CompanyAuthenticationVO> convertTo(Page<CompanyAuthenticationDO> page) {
        return page.map(CompanyAuthenticationConverter::convertTo);
    }
}