package com.easy.cloud.web.module.sms.biz.converter;

import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import com.easy.cloud.web.module.sms.api.vo.SmsVO;
import com.easy.cloud.web.module.sms.biz.domain.SmsDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Sms转换器
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
public class SmsConverter {

  /**
   * DTO转为DO
   *
   * @param sms 转换数据
   * @return com.easy.cloud.web.module.sms.domain.db.SmsDO
   */
  public static SmsDO convertTo(SmsDTO sms) {
    return SmsDO.builder()
        .id(sms.getId())
        .code(sms.getCode())
        .tel(sms.getTel())
        .content(sms.getContent())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param sms 转换数据
   * @return com.easy.cloud.web.module.sms.domain.vo.SmsVO
   */
  public static SmsVO convertTo(SmsDO sms) {
    return SmsVO.builder()
        .id(sms.getId())
        .code(sms.getCode())
        .tel(sms.getTel())
        .content(sms.getContent())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param smss 转换数据
   * @return com.easy.cloud.web.module.sms.domain.vo.SmsVO
   */
  public static List<SmsVO> convertTo(List<SmsDO> smss) {
    return smss.stream()
        .map(SmsConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.module.sms.domain.vo.SmsVO
   */
  public static Page<SmsVO> convertTo(Page<SmsDO> page) {
    return page.map(SmsConverter::convertTo);
  }
}