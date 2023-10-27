package com.easy.cloud.web.component.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.service.upms.api.dto.SysLogDTO;
import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
import com.easy.cloud.web.service.upms.api.feign.UpmsLogFeignClientService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志处理类
 *
 * @author GR
 * @date 2021-8-4 14:03
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {

  private final UpmsLogFeignClientService upmsLogFeignClientService;

  @SneakyThrows
  @Around("@annotation(sysLog)")
  public Object around(ProceedingJoinPoint proceedingJoinPoint, SysLog sysLog) {
    // 构建日志对象
    SysLogDTO sysLogDTO = this.buildLogInfo();
    Object[] objs = proceedingJoinPoint.getArgs();
    sysLogDTO.setName(sysLog.value()).setParams(JSONUtil.toJsonStr(objs));
    // 发送异步日志事件
    Long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } catch (Exception e) {
      sysLogDTO.setType(OperationLogType.EXCEPTION);
      sysLogDTO.setException(e.getMessage());
      throw e;
    } finally {
      Long endTime = System.currentTimeMillis();
      sysLogDTO.setElapsedTime(endTime - startTime);
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
    sysLogDTO.setType(OperationLogType.NORMAL);
    sysLogDTO.setRequestUri(URLUtil.getPath(request.getRequestURI()));
    sysLogDTO.setMethod(request.getMethod());
    sysLogDTO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
    sysLogDTO.setParams(HttpUtil.toParams(request.getParameterMap()));
    return sysLogDTO;
  }

  /**
   * 远程调用日志存储
   *
   * @param sysLogDTO 日志信息
   */
  @Async
  public void remoteLogSave(SysLogDTO sysLogDTO) {
    // 存储日志，走内部接口
    upmsLogFeignClientService.saveLog(sysLogDTO, SecurityConstants.INNER_ORIGIN);
  }
}
