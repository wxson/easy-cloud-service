package com.easy.cloud.web.component.mysql.interceptor;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.util.ReflectUtils;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

/**
 * @author GR
 * @date 2021-11-25 16:35
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        SqlCommandType sqlCommandType = ((MappedStatement) invocation.getArgs()[0]).getSqlCommandType();
        Object[] args = invocation.getArgs();
        if (args.length <= 1) {
            return invocation.proceed();
        }

        Object target = args[1];
        if (null == target) {
            return invocation.proceed();
        }

        // 插入
        if (SqlCommandType.INSERT == sqlCommandType) {
            return this.insert(invocation, target);
        }

        // 更新
        if (SqlCommandType.UPDATE == sqlCommandType) {
            return this.update(invocation, target);
        }

        return invocation.proceed();
    }

    /**
     * 插入操作
     *
     * @param invocation invocation
     * @return java.lang.Object
     */
    private Object insert(Invocation invocation, Object target) throws Throwable {

        if (ReflectUtils.hasProperties("createAt", target)) {
            ReflectUtils.setPropertiesValue("createAt", DateUtil.now(), target);
        }

        //获取认证用户,获取失败，直接执行
        AuthenticationUser authenticationUser;
        try {
            authenticationUser = this.getAuthenticationUser();
        } catch (Exception ignore) {
            return invocation.proceed();
        }

        if (ReflectUtils.hasProperties("creatorAt", target)) {
            ReflectUtils.setPropertiesValue("creatorAt", authenticationUser.getId(), target);
        }

        return invocation.proceed();
    }

    /**
     * 更新操作
     *
     * @param invocation invocation
     * @return java.lang.Object
     */
    private Object update(Invocation invocation, Object target) throws Throwable {
        if (ReflectUtils.hasProperties("updateAt", target)) {
            ReflectUtils.setPropertiesValue("updateAt", DateUtil.now(), target);
        }

        //获取认证用户,获取失败，直接执行
        AuthenticationUser authenticationUser;
        try {
            authenticationUser = this.getAuthenticationUser();
        } catch (Exception e) {
            return invocation.proceed();
        }

        if (ReflectUtils.hasProperties("updaterAt", target)) {
            ReflectUtils.setPropertiesValue("updaterAt", authenticationUser.getId(), target);
        }
        return invocation.proceed();
    }


    /**
     * 获取认证用户
     *
     * @return com.easy.cloud.web.component.security.domain.AuthenticationUser
     */
    private AuthenticationUser getAuthenticationUser() {
        return SecurityUtils.getAuthenticationUser();
    }
}
