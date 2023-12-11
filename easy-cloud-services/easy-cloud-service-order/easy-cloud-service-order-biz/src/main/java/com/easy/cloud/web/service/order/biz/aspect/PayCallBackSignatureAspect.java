package com.easy.cloud.web.service.order.biz.aspect;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.order.api.enums.PayTypeEnum;
import com.easy.cloud.web.service.order.biz.annotation.PayCallBackSignature;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author GR
 * @date 2021-11-27 10:35
 */
@Slf4j
@Aspect
@Component
public class PayCallBackSignatureAspect {

  /**
   * 支付回调拦截
   *
   * @param proceedingJoinPoint  切点
   * @param payCallBackSignature 注解
   * @return java.lang.Object
   */
  @SneakyThrows
  @Around("@annotation(payCallBackSignature)")
  public Object payCallBackSignature(ProceedingJoinPoint proceedingJoinPoint,
      PayCallBackSignature payCallBackSignature) {
    if (PayTypeEnum.WX_PAY == payCallBackSignature.payType()) {
      return this.wxCallBackSignature(proceedingJoinPoint);
    }

    return this.aliCallBackSignature(proceedingJoinPoint);
  }

  /**
   * 微信支付校验 商户必须使用微信支付公钥验证回调的签名。 文档：https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay3_3.shtml
   *
   * @param proceedingJoinPoint 切点
   * @return java.lang.Object
   */
  private Object wxCallBackSignature(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (null == requestAttributes) {
      throw new BusinessException("获取当前请求参数异常");
    }

    try {
      // 获取当前请求
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      // 微信签名
      String wxPaySignature = request.getHeader("Wechatpay-Signature");
      Signature signer = Signature.getInstance("SHA256withRSA");
      // TODO 待实现签名校验
      // signer.initVerify("certificate");
      signer.update(wxPaySignature.getBytes(StandardCharsets.UTF_8));
      if (signer.verify(Base64Utils.decodeFromString(wxPaySignature))) {
        return proceedingJoinPoint.proceed();
      }
      return proceedingJoinPoint.proceed();
    } catch (Exception exception) {
      throw new BusinessException("当前支付回调通知为不可信任的回调通知");
    }
  }

  /**
   * 支付宝支付校验
   *
   * @param proceedingJoinPoint 切点
   * @return java.lang.Object
   */
  private Object aliCallBackSignature(ProceedingJoinPoint proceedingJoinPoint) {
    return null;
  }
}
