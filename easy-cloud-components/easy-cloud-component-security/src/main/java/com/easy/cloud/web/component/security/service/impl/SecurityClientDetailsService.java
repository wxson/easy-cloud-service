package com.easy.cloud.web.component.security.service.impl;

import com.easy.cloud.web.component.core.constants.CacheConstants;
import com.easy.cloud.web.component.core.tenant.TenantContextHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;


/**
 * @author GR
 * @date 2021-3-26 17:07
 */
public class SecurityClientDetailsService extends JdbcClientDetailsService {

    public SecurityClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId 客户ID
     * @throws InvalidClientException
     */
    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId 客户端ID
     * @return ClientDetails
     */
//    @Override
//    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
//    public ClientDetails loadClientByClientId(String clientId) {
//        super.setSelectClientDetailsSql(
//                String.format(OauthConstants.DEFAULT_SELECT_STATEMENT, TenantContextHolder.getTenantId()));
//        return super.loadClientByClientId(clientId);
//    }
}
