package com.easy.cloud.web.module.certification.biz.adapter;

/**
 * @author GR
 * @date 2024/2/19 11:11
 */
public interface ICompanyAuthenticationAdapter {
    /**
     * 认证成功
     *
     * @param userId 认证的用户
     */
    void authenticationSuccess(String userId);

    /**
     * 认证失败
     *
     * @param userId     认证用户
     * @param failReason 认证失败原因
     */
    void authenticationFail(String userId, String failReason);
}
