package com.easy.cloud.web.component.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.module.log.api.dto.SysLogDTO;
import com.easy.cloud.web.module.log.api.dto.SysLogType;
import com.easy.cloud.web.module.log.api.feign.SysLogModuleApi;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 日志处理类
 *
 * @author GR
 * @date 2021-8-4 14:03
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class SysLogAspect {

    private final SysLogModuleApi sysLogModuleApi;

    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, SysLog sysLog) {
        // 构建日志对象
        SysLogDTO sysLogDTO = this.buildLogInfo();
        // 读取参数
        Object[] objs = proceedingJoinPoint.getArgs();
        // 日志内容
        sysLogDTO.setName(sysLog.value());
        // 操作类型
        sysLogDTO.setAction(sysLog.action().name());
        // 执行参数
        sysLogDTO.setParams(JSONUtil.toJsonStr(objs));
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            sysLogDTO.setType(SysLogType.EXCEPTION);
            sysLogDTO.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            sysLogDTO.setElapsedTime(Long.valueOf(endTime - startTime).intValue());
            this.remoteLogSave(sysLogDTO);
        }
    }

    /**
     * 构建日志信息
     *
     * @return
     */
    private SysLogDTO buildLogInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLogDTO sysLogDTO = SysLogDTO.builder().build();
        sysLogDTO.setType(SysLogType.NORMAL);
        sysLogDTO.setRequestPath(URLUtil.getPath(request.getRequestURI()));
        sysLogDTO.setMethod(request.getMethod());
        sysLogDTO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        sysLogDTO.setParams(HttpUtil.toParams(request.getParameterMap()));
        sysLogDTO.setRemoteAddr(request.getRemoteAddr());
        return sysLogDTO;
    }

    /**
     * 远程调用日志存储
     *
     * @param sysLogDTO 日志信息
     */
    @Async
    public void remoteLogSave(SysLogDTO sysLogDTO) {
        try {
            // 存储日志，走内部接口
            sysLogModuleApi.saveLog(sysLogDTO);
        } catch (Exception exception) {
            log.warn("execute remote log save fail：{}", exception.getMessage());
        }
    }
}
