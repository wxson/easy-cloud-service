package com.easy.cloud.web.component.security.mobile;

import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import com.easy.cloud.web.component.security.util.SecurityPreAuthenticationChecks;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Objects;

/**
 * 移动授权登录凭证
 *
 * @author GR
 * @date 2021-11-3 10:40
 */
@Slf4j
@AllArgsConstructor
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final UserDetailsChecker userDetailsChecker = new SecurityPreAuthenticationChecks();

    private final ISecurityUserDetailsService securityUserDetailsService;

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        // 获取认证用户实体
        String principal = mobileAuthenticationToken.getPrincipal().toString();
        // 调取第三方授权
        UserDetails userDetails = securityUserDetailsService.loadUserBySocial(principal);
        if (Objects.isNull(userDetails)) {
            log.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }
        // 检查账号状态
        userDetailsChecker.check(userDetails);
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
