package com.easy.cloud.web.module.sms.biz.service.impl;

import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import com.easy.cloud.web.module.sms.api.vo.SmsVO;
import com.easy.cloud.web.module.sms.biz.converter.SmsConverter;
import com.easy.cloud.web.module.sms.biz.domain.SmsDO;
import com.easy.cloud.web.module.sms.biz.repository.SmsRepository;
import com.easy.cloud.web.module.sms.biz.service.ISmsService;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Sms 业务逻辑
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Slf4j
@Service
public class SmsServiceImpl implements ISmsService {

  @Autowired
  private SmsRepository smsRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public SmsVO save(SmsDTO smsDTO) {
    // 转换成DO对象
    SmsDO sms = SmsConverter.convertTo(smsDTO);
    // TODO 校验逻辑

    // 存储
    smsRepository.save(sms);
    // 转换对象
    return SmsConverter.convertTo(sms);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public SmsVO update(SmsDTO smsDTO) {
    // 转换成DO对象
    SmsDO sms = SmsConverter.convertTo(smsDTO);
    if (Objects.isNull(sms.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    smsRepository.save(sms);
    // 转换对象
    return SmsConverter.convertTo(sms);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean removeById(Long smsId) {
    // TODO 业务逻辑校验

    // 删除
    smsRepository.deleteById(smsId);
    return true;
  }

  @Override
  public SmsVO detailById(Long smsId) {
    // TODO 业务逻辑校验

    // 删除
    SmsDO sms = smsRepository.findById(smsId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return SmsConverter.convertTo(sms);
  }

  @Override
  public List<SmsVO> list() {
    // 获取列表数据
    List<SmsDO> smss = smsRepository.findAll();
    return SmsConverter.convertTo(smss);
  }

  @Override
  public Page<SmsVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return SmsConverter.convertTo(smsRepository.findAll(pageable));
  }
}