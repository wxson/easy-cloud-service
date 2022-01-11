package com.easy.cloud.web.module.sms.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.sms.domain.db.SmsSendRecordDO;
import com.easy.cloud.web.module.sms.domain.dto.SmsDTO;
import com.easy.cloud.web.module.sms.mapper.DbSmsSendRecordMapper;
import com.easy.cloud.web.module.sms.service.ISmsSendRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author GR
 * @date 2021-12-9 19:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsSendRecordServiceImpl extends ServiceImpl<DbSmsSendRecordMapper, SmsSendRecordDO> implements ISmsSendRecordService {

    private final static String SMS_REDIS_PREFIX = "easy_cloud:sms:";

    private final static String SMS_CODE_REDIS_PREFIX = SMS_REDIS_PREFIX + "code:{}";

    private final static String SMS_CONTENT_REDIS_PREFIX = SMS_REDIS_PREFIX + "content:{}";

    /**
     * 有效期120秒
     */
    private final static Integer SMS_EXPIRE = 120;

    private final RedisTemplate redisTemplate;

    @Override
    public Boolean callBack(SmsDTO smsDTO) {
        // 电话不能为空
        if (StrUtil.isBlank(smsDTO.getTel())) {
            throw new BusinessException("电话不能为空");
        }

        // 验证码不能为空
        if (StrUtil.isBlank(smsDTO.getCode())) {
            throw new BusinessException("验证码不能为空");
        }

        redisTemplate.opsForValue().set(StrUtil.format(SMS_CODE_REDIS_PREFIX, smsDTO.getTel()), smsDTO.getCode(), SMS_EXPIRE, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(StrUtil.format(SMS_CONTENT_REDIS_PREFIX, smsDTO.getTel()), smsDTO.getContent(), SMS_EXPIRE, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Boolean bindTel(SmsDTO smsDTO) {
        // 电话不能为空
        if (StrUtil.isBlank(smsDTO.getTel())) {
            throw new BusinessException("电话不能为空");
        }

        // 验证码不能为空
        if (StrUtil.isBlank(smsDTO.getCode())) {
            throw new BusinessException("验证码不能为空");
        }

        // 获取短信验证码
        Object smsCodeObj = redisTemplate.opsForValue().get(StrUtil.format(SMS_CODE_REDIS_PREFIX, smsDTO.getTel()));
        Object smsContentObj = redisTemplate.opsForValue().get(StrUtil.format(SMS_CONTENT_REDIS_PREFIX, smsDTO.getTel()));
        if (Objects.isNull(smsCodeObj)) {
            throw new BusinessException("当前短信验证码无效");
        }

        // 获取短信验证码
        String smsCode = smsCodeObj.toString();
        if (!smsCode.equals(smsDTO.getCode())) {
            throw new BusinessException("当前验证码不匹配，请重新输入");
        }

        // 创建短信记录
        SmsSendRecordDO smsSendRecordDO = SmsSendRecordDO.build().setTel(smsDTO.getTel()).setCode(smsCode);
        if (Objects.nonNull(smsContentObj)) {
            smsSendRecordDO.setContent(smsContentObj.toString());
        }
        this.save(smsSendRecordDO);
        return true;
    }

    @Override
    public Boolean getCodeByTel(SmsDTO smsDTO) {
        // 获取手机号
        String tel = smsDTO.getTel();
        // 电话不能为空
        if (StrUtil.isBlank(tel)) {
            throw new BusinessException("电话不能为空");
        }

        // 电话
        if (!Validator.isMobile(tel)) {
            throw new BusinessException("填写的电话信息有误");
        }

        // TODO 执行获取短信验证码操作

        return true;
    }
}
