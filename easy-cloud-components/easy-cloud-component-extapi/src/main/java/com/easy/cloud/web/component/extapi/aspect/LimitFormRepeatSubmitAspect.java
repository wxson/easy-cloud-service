package com.easy.cloud.web.component.extapi.aspect;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.core.util.ReflectUtils;
import com.easy.cloud.web.component.extapi.annotation.LimitFormRepeatSubmit;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 表单重复提交AOP
 *
 * @author GR
 * @date 2021-4-9 11:11
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LimitFormRepeatSubmitAspect {

    private final RedisTemplate redisTemplate;

    /**
     * 执行表单重复提交拦截
     *
     * @param proceedingJoinPoint   proceedingJoinPoint
     * @param limitFormRepeatSubmit 表单重复提交注解注解
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("@annotation(limitFormRepeatSubmit)")
    public Object limitFormRepeatSubmitAspect(ProceedingJoinPoint proceedingJoinPoint, LimitFormRepeatSubmit limitFormRepeatSubmit) {
        // 获取当前请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            // 获取当前请求
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 是否为内部请求
            String header = request.getHeader(SecurityConstants.ORIGIN);
            if (StrUtil.isNotBlank(header) && header.equals(SecurityConstants.INNER_ORIGIN)) {
                return proceedingJoinPoint.proceed();
            }
            // 获取key
            String onlyKey = this.buildOnlyKey(proceedingJoinPoint, limitFormRepeatSubmit);
            // 校验KEY是否存在
            if (!this.verifyRepeat(onlyKey, limitFormRepeatSubmit)) {
                // 获取当前响应体
                HttpServletResponse httpServletResponse = ((ServletRequestAttributes) requestAttributes).getResponse();
                if (Objects.nonNull(httpServletResponse)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    HttpResult<Boolean> httpResult = HttpResult.fail(HttpResultEnum.REPEAT_SUBMIT.getCode(), HttpResultEnum.REPEAT_SUBMIT.getDesc());
                    String jsonStr = JSONUtil.toJsonStr(httpResult);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                }
                // 放行
                return proceedingJoinPoint.proceed();
            }
        }
        return null;
    }

    /**
     * 1.在调用接口之前获取用户ID
     * 2.根据当前用户ID查询Redis缓存中是否有幂等性key、如果key不存在返回true，进入Controller方法执行api
     * 3.如果幂等性key在Redis缓存中，抛出异常提示客户不能重复提交
     *
     * @param key : 更新接口唯一key
     */
    private Boolean verifyRepeat(String key, LimitFormRepeatSubmit limitFormRepeatSubmit) {
        final Object objectValueByKey = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(objectValueByKey)) {
            //设置幂等性key、过期时间为30s
            redisTemplate.opsForValue().set(key, key, limitFormRepeatSubmit.expireTime(), TimeUnit.SECONDS);
            return Boolean.FALSE;
        }
        //保证每个接口对应的RepeatKey 只能访问一次，保证接口幂等性问题
        return Boolean.TRUE;
    }

    /**
     * 构建唯一key
     *
     * @param proceedingJoinPoint   point
     * @param limitFormRepeatSubmit 注解
     * @return java.lang.String
     */
    private String buildOnlyKey(ProceedingJoinPoint proceedingJoinPoint, LimitFormRepeatSubmit limitFormRepeatSubmit) {
        String prefix = limitFormRepeatSubmit.prefix();
        String value = limitFormRepeatSubmit.value();
        // 获取userId
        String userId = "";
        try {
            userId = SecurityUtils.getAuthenticationUser().getId();
        } catch (Exception ignored) {

        }

        // 如果当前不使用token登录
        if (StrUtil.isBlank(userId)) {
            Object[] joinPointArgs = proceedingJoinPoint.getArgs();
            if (joinPointArgs.length > 0) {
                Object targetObject = joinPointArgs[0];
                String filed = limitFormRepeatSubmit.readFirstObjectFiled();
                Object propertiesValue = ReflectUtils.getPropertiesValue(filed, targetObject);
                if (Objects.nonNull(propertiesValue)) {
                    userId = propertiesValue.toString();
                }
            }
        }

        if (StrUtil.isBlank(userId)) {
            userId = "default";
        }

        // 格式：prefix:userId:value
        return StrBuilder.create(prefix).append(":").append(userId).append(":").append(value).toString();
    }
}
