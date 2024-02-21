package com.easy.cloud.web.module.certification.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.certification.api.dto.UserAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.UserAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.domain.UserAuthenticationDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserAuthentication转换器
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
public class UserAuthenticationConverter {

    /**
     * DTO转为DO
     *
     * @param UserAuthentication 转换数据
     * @return com.easy.cloud.web.module.certification.biz.domain.UserAuthenticationDO
     */
    public static UserAuthenticationDO convertTo(UserAuthenticationDTO UserAuthentication) {
        UserAuthenticationDO userAuthenticationDO = UserAuthenticationDO.builder().build();
        BeanUtils.copyProperties(UserAuthentication, userAuthenticationDO, true);
        return userAuthenticationDO;
    }

    /**
     * DO转为VO
     *
     * @param userAuthentication 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.UserAuthenticationVO
     */
    public static UserAuthenticationVO convertTo(UserAuthenticationDO userAuthentication) {
        UserAuthenticationVO userAuthenticationVO = UserAuthenticationVO.builder().build();
        BeanUtils.copyProperties(userAuthentication, userAuthenticationVO, true);
        return userAuthenticationVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param userAuthentications 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.UserAuthenticationVO
     */
    public static List<UserAuthenticationVO> convertTo(List<UserAuthenticationDO> userAuthentications) {
        return userAuthentications.stream()
                .map(UserAuthenticationConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.member.api.vo.UserAuthenticationVO
     */
    public static Page<UserAuthenticationVO> convertTo(Page<UserAuthenticationDO> page) {
        return page.map(UserAuthenticationConverter::convertTo);
    }
}