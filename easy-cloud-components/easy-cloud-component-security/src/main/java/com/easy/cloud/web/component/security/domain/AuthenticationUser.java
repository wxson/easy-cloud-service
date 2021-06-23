package com.easy.cloud.web.component.security.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 认证用户
 *
 * @author GR
 * @date 2021-4-1 9:25
 */
@Getter
@Setter
@Accessors(chain = true)
public class AuthenticationUser extends User {

    private static final long serialVersionUID = -4174967039482588731L;
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户角色
     */
    private String channelId;
    /**
     * 租户ID
     */
    private String tenantId;

    public AuthenticationUser(String id, String username, String password, String channelId,
                              boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                              boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.channelId = channelId;
    }
}
