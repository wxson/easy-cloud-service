package com.easy.cloud.web.component.security.configuration.converter;

import com.easy.cloud.web.component.security.constants.SecurityConstants;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author GR
 * @date 2021-4-2 16:33
 */
public class SecurityUserAuthenticationConverter implements UserAuthenticationConverter {

    private Collection<? extends GrantedAuthority> defaultAuthorities;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            // 自定义principal对象
            AuthenticationUser authenticationUser = new AuthenticationUser(
                    (String) map.get(SecurityConstants.AUTHORIZATION_USER_ID),
                    (String) map.get(SecurityConstants.AUTHORIZATION_USER_NAME),
                    "N/A",
                    (String) map.get(SecurityConstants.AUTHORIZATION_USER_CHANEL_ID),
                    true, true, true, true, authorities
            );
            // 设置租户ID
            authenticationUser.setTenantId((String) map.get(SecurityConstants.AUTHORIZATION_USER_TENANT_ID));

            return new UsernamePasswordAuthenticationToken(authenticationUser, "N/A", authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }

        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }

        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }

        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
