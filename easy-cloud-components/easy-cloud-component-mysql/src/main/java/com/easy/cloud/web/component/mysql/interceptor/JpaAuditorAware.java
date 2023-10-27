package com.easy.cloud.web.component.mysql.interceptor;

import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * @author GR
 * @date 2023/10/24 16:40
 */
@Slf4j
@Component
public class JpaAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    try {
      AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
      return Optional.of(authenticationUser.getId());
    } catch (Exception exception) {
      log.info("JpaInterceptor Exception:{}", exception.getMessage());
    }
    return Optional.empty();
  }
}
