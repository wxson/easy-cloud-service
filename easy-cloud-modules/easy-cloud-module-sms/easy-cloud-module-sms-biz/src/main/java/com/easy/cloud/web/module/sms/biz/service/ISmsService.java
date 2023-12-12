package com.easy.cloud.web.module.sms.biz.service;

import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import com.easy.cloud.web.module.sms.api.vo.SmsVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Sms interface
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
public interface ISmsService {

  /**
   * 新增数据
   *
   * @param smsDTO 保存参数
   * @return com.easy.cloud.web.module.sms.domain.vo.SmsVO
   */
  SmsVO save(SmsDTO smsDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param smsDTO 保存参数
   * @return com.easy.cloud.web.module.sms.domain.vo.SmsVO
   */
  SmsVO update(SmsDTO smsDTO);

  /**
   * 根据ID删除数据
   *
   * @param smsId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String smsId);

  /**
   * 根据ID获取详情
   *
   * @param smsId 对象ID
   * @return java.lang.Boolean
   */
  SmsVO detailById(String smsId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.module.sms.domain.vo.SmsVO> 返回列表数据
   */
  List<SmsVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.module.sms.domain.vo.SmsVO> 返回列表数据
   */
  Page<SmsVO> page(int page, int size);
}