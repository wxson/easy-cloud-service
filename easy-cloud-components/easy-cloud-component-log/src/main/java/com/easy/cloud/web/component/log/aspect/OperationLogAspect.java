package com.easy.cloud.web.component.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.log.annotation.OperationLog;
import com.easy.cloud.web.service.upms.api.dto.OperationLogDTO;
import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
import com.easy.cloud.web.service.upms.api.feign.UpmsLogFeignClientService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OperationLogAspect {

  private final UpmsLogFeignClientService upmsLogFeignClientService;

  @SneakyThrows
  @Around("@annotation(operationLog)")
  public Object around(ProceedingJoinPoint proceedingJoinPoint, OperationLog operationLog) {
    // 构建日志对象
    OperationLogDTO operationLogDTO = this.buildLogInfo();
    Object[] objs = proceedingJoinPoint.getArgs();
    operationLogDTO.setName(operationLog.value()).setParams(JSONUtil.toJsonStr(objs));
    // 发送异步日志事件
    Long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } catch (Exception e) {
      operationLogDTO.setType(OperationLogType.EXCEPTION);
      operationLogDTO.setException(e.getMessage());
      throw e;
    } finally {
      Long endTime = System.currentTimeMillis();
      operationLogDTO.setElapsedTime(endTime - startTime);
      this.remoteLogSave(operationLogDTO);
    }
  }

  /**
   * 构建日志信息
   *
   * @return
   */
  private OperationLogDTO buildLogInfo() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    OperationLogDTO operationLogDTO = OperationLogDTO.builder().build();
    operationLogDTO.setType(OperationLogType.NORMAL);
    operationLogDTO.setRequestUri(URLUtil.getPath(request.getRequestURI()));
    operationLogDTO.setMethod(request.getMethod());
    operationLogDTO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
    operationLogDTO.setParams(HttpUtil.toParams(request.getParameterMap()));
    return operationLogDTO;
  }

  /**
   * 远程调用日志存储
   *
   * @param operationLogDTO 日志信息
   */
  @Async
  public void remoteLogSave(OperationLogDTO operationLogDTO) {
    // 存储日志，走内部接口
    upmsLogFeignClientService.saveLog(operationLogDTO, SecurityConstants.INNER_ORIGIN);
  }
}
