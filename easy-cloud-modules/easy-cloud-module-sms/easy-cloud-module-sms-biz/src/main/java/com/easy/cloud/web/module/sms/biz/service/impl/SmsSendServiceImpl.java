package com.easy.cloud.web.module.sms.biz.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.mysql.query.SpecificationWrapper;
import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import com.easy.cloud.web.module.sms.api.dto.SmsSendDTO;
import com.easy.cloud.web.module.sms.api.dto.SmsValidateCodeDTO;
import com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum;
import com.easy.cloud.web.module.sms.api.enums.SmsSendStatusEnum;
import com.easy.cloud.web.module.sms.api.vo.SmsSendVO;
import com.easy.cloud.web.module.sms.api.vo.SmsVO;
import com.easy.cloud.web.module.sms.biz.domain.SmsDO;
import com.easy.cloud.web.module.sms.biz.repository.SmsRepository;
import com.easy.cloud.web.module.sms.biz.service.ISmsClient;
import com.easy.cloud.web.module.sms.biz.service.ISmsSendService;
import com.easy.cloud.web.module.sms.biz.service.ISmsService;
import com.easy.cloud.web.module.sms.biz.service.client.properties.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author GR
 * @date 2024-1-28 23:19
 */
@Slf4j
@Service
public class SmsSendServiceImpl implements ISmsSendService, ApplicationContextAware {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private SmsRepository smsRepository;

    /**
     * 平台短信客户端
     */
    private final EnumMap<SmsChannelEnum, ISmsClient> smsClients = new EnumMap<>(SmsChannelEnum.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(ISmsClient.class).values()
                .forEach(smsClient -> smsClients.put(smsClient.channel(), smsClient));
    }

    @Override
    public void sendSms(String tel) {
        // 创建短信验证码
        String smsCode = this.createSmsCode();
        // TODO 获取短信模板ID

        // 构建短信发送内容
        SmsSendDTO smsSendDTO = SmsSendDTO.builder().tel(tel).build();
        // 构建短信内容
        smsSendDTO.setSignature(smsProperties.getSignature());
        smsSendDTO.setTemplateId(smsSendDTO.getTemplateId());
        Map<String, Object> smsContentMap = new HashMap<>();
        smsContentMap.put("code", smsCode);
        smsSendDTO.setContent(smsContentMap);

        // 存储验证码记录
        SmsVO smsVO = smsService.save(SmsDTO.builder().code(smsCode)
                .tel(smsSendDTO.getTel())
                .content(JSONUtil.toJsonStr(smsSendDTO.getContent()))
                .sendStatus(SmsSendStatusEnum.UN_SEND).build());
        // 设置当前短信内容ID
        smsSendDTO.setRecordId(smsVO.getId());
        // 异步发送短信信息
        this.asyncSendSms(smsSendDTO);
    }

    /**
     * 创建短信验证码
     *
     * @return java.lang.String
     */
    private String createSmsCode() {
        // 获取验证码长度
        int codeLength = Optional.ofNullable(smsProperties.getCodeLength()).orElse(4);
        return RandomUtil.randomNumbers(codeLength);
    }

    /**
     * 异步发送短信信息
     *
     * @param smsSendDTO 发送的短信内容
     */
    @Async
    public void asyncSendSms(SmsSendDTO smsSendDTO) {
        try {
            // 根据配置当前项目所需的短信渠道信息
            SmsChannelEnum smsChannelEnum = SmsChannelEnum.valueOf(smsProperties.getChannel());
            // 短信渠道信息为空
            if (Objects.isNull(smsChannelEnum)) {
                // 更新短信信息发送状态
                smsService.update(SmsDTO.builder().id(smsSendDTO.getRecordId()).used(false)
                        .sendStatus(SmsSendStatusEnum.FAIL).remark("获取渠道信息失败").build());
                return;
            }
            // 发送短信
            SmsSendVO smsSendVO = smsClients.get(smsChannelEnum).sendSingleSms(smsSendDTO);
            // 更新短信信息发送状态
            smsService.update(SmsDTO.builder().id(smsSendDTO.getRecordId()).used(false)
                    .sendStatus(SmsSendStatusEnum.SUCCESS).remark(smsSendVO.getMessage()).build());
        } catch (Exception exception) {
            // 更新短信信息发送状态
            smsService.update(SmsDTO.builder().id(smsSendDTO.getRecordId()).used(false)
                    .sendStatus(SmsSendStatusEnum.FAIL).build());
        }
    }

    @Override
    public void validateSmsCode(SmsValidateCodeDTO smsValidateCodeDTO) {
        // 通过手机号获取最近的一个验证码
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<SmsDO> page = smsRepository.findAll(SpecificationWrapper.where(SmsDO::getTel, smsValidateCodeDTO.getTel())
                .orderDesc(SmsDO::getCreateAt), pageRequest);
        Optional<SmsDO> smsOptional = page.getContent().stream().findFirst();
        if (!smsOptional.isPresent()) {
            throw new BusinessException("当前短信验证码信息不存在");
        }

        // 获取短信验证码信息
        SmsDO smsDO = smsOptional.get();
        // 若当前短信验证为通过，则抛出异常
        if (!smsDO.getCode().equals(smsValidateCodeDTO.getCode())) {
            throw new BusinessException("输入验证码错误");
        }

        // 验证码是否已使用
        if (smsDO.getUsed()) {
            throw new BusinessException("当前验证码已使用");
        }

        // TODO 验证码是否过期

        // 标记已使用
        smsDO.setUsed(true);
        smsRepository.save(smsDO);
    }
}
