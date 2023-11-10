package com.easy.cloud.web.component.security.domain;

import cn.hutool.core.util.StrUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

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
   * 角色：role1:role2:role3，默认单个角色
   */
  private String channel;
  /**
   * 租户
   */
  private String tenant;

  public AuthenticationUser(String id, String username, String password, String channel,
      String tenant, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
    this.id = id;
    this.channel = channel;
    this.tenant = tenant;
  }

  /**
   * 获取多个角色
   *
   * @return
   */
  public List<String> getChannels() {
    return Arrays.asList(channel.split(StrUtil.COLON));
  }
}
