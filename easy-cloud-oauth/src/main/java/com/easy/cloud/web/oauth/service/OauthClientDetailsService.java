package com.easy.cloud.web.oauth.service;

import com.easy.cloud.web.component.core.constants.CacheConstants;
import com.easy.cloud.web.component.core.tenant.TenantContextHolder;
import com.easy.cloud.web.oauth.constants.OauthConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author GR
 * @date 2021-8-12 15:03
 */
@Service
public class OauthClientDetailsService extends JdbcClientDetailsService {

    public OauthClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId 客户端ID
     * @return ClientDetails
     */
    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        super.setSelectClientDetailsSql(
                String.format(OauthConstants.DEFAULT_SELECT_STATEMENT, TenantContextHolder.getTenantId()));
        return super.loadClientByClientId(clientId);
    }
}
