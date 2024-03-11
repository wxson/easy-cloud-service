package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author GR
 * @date 2024-3-10 21:05
 */
public class SimpleAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final ISecurityUserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SimpleAuthenticationProvider(ISecurityUserDetailsService userDetailsService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String presentedPassword = authentication.getCredentials().toString();
        if (!bCryptPasswordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            if (!bCryptPasswordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                throw new BusinessException(HttpResultEnum.USER_PASSWORD.getCode(), HttpResultEnum.USER_PASSWORD.getDesc());
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser = this.userDetailsService.loadUserByUsername(username);
        if (loadedUser == null) {
            throw new BusinessException(HttpResultEnum.USER_PASSWORD.getCode(), HttpResultEnum.USER_PASSWORD.getDesc());
        }
        return loadedUser;
    }
}