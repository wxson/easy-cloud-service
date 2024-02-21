package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.module.certification.api.dto.UserAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.vo.UserAuthenticationVO;

/**
 * UserAuthentication interface
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
public interface IUserAuthenticationService {

    /**
     * 更新数据，默认全量更新
     *
     * @param userAuthenticationDTO 保存参数
     * @return com.easy.cloud.web.service.member.api.vo.UserAuthenticationVO
     */
    UserAuthenticationVO addOrUpdate(UserAuthenticationDTO userAuthenticationDTO);

    /**
     * 根据ID获取详情
     *
     * @return java.lang.Boolean
     */
    UserAuthenticationVO detailById(String userId);
}