package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.api.dto.PersonalAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.dto.UserAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import com.easy.cloud.web.module.certification.api.vo.PersonalAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.adapter.IPersonalAuthenticationAdapter;
import com.easy.cloud.web.module.certification.biz.converter.PersonalAuthenticationConverter;
import com.easy.cloud.web.module.certification.biz.domain.PersonalAuthenticationDO;
import com.easy.cloud.web.module.certification.biz.repository.PersonalAuthenticationRepository;
import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
import com.easy.cloud.web.module.certification.biz.service.ICertificationService;
import com.easy.cloud.web.module.certification.biz.service.IPersonalAuthenticationService;
import com.easy.cloud.web.module.certification.biz.service.IUserAuthenticationService;
import com.easy.cloud.web.module.certification.biz.service.certification.entity.CertificationBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * PersonalAuthentication 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Slf4j
@Service
public class PersonalAuthenticationServiceImpl implements IPersonalAuthenticationService {

    @Autowired
    private PersonalAuthenticationRepository personalAuthenticationRepository;

    @Autowired
    private IAuthenticationRecordService authenticationRecordService;

    @Autowired
    private ICertificationService certificationService;

    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    /**
     * 认证之前校验
     *
     * @param personalAuthenticationDTO 认证参数
     */
    private void authenticationBeforeValid(PersonalAuthenticationDTO personalAuthenticationDTO) {
        // 手机号校验
        if (StrUtil.isNotBlank(personalAuthenticationDTO.getTel())
                && !PhoneUtil.isPhone(personalAuthenticationDTO.getTel())) {
            throw new BusinessException("当前手机号格式不正确");
        }

        // 身份证验证
        if (StrUtil.isNotBlank(personalAuthenticationDTO.getIdentityCard())
                && !IdcardUtil.isValidCard18(personalAuthenticationDTO.getIdentityCard())) {
            throw new BusinessException("当前身份证格式不正确");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PersonalAuthenticationVO submit(PersonalAuthenticationDTO personalAuthenticationDTO) {
        // 校验
        this.authenticationBeforeValid(personalAuthenticationDTO);
        // 转换成DO对象
        PersonalAuthenticationDO personalAuthentication = PersonalAuthenticationConverter.convertTo(personalAuthenticationDTO);
        // TODO 校验逻辑

        // 存储认证用户
        personalAuthentication.setUserId(SecurityUtils.getAuthenticationUser().getId());
        // 存储
        personalAuthenticationRepository.save(personalAuthentication);
        // 更新审核信息
        this.updateAuthenticationStatus(personalAuthentication);
        // 转换对象
        return PersonalAuthenticationConverter.convertTo(personalAuthentication);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PersonalAuthenticationVO update(PersonalAuthenticationDTO personalAuthenticationDTO) {
        // 转换成DO对象
        if (Objects.isNull(personalAuthenticationDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // 校验
        this.authenticationBeforeValid(personalAuthenticationDTO);

        PersonalAuthenticationDO personalAuthentication = personalAuthenticationRepository.findById(personalAuthenticationDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 若已认证成功，则禁止修改
        if (personalAuthentication.getAuthenticated()) {
            // 转换对象
            return PersonalAuthenticationConverter.convertTo(personalAuthentication);
        }

        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(personalAuthenticationDTO, personalAuthentication, true);
        // TODO 业务逻辑校验

        // 存储认证用户
        personalAuthentication.setUserId(SecurityUtils.getAuthenticationUser().getId());
        // 更新审核信息
        this.updateAuthenticationStatus(personalAuthentication);
        // 转换对象
        return PersonalAuthenticationConverter.convertTo(personalAuthentication);
    }

    @Override
    public PersonalAuthenticationVO updateAuthenticationStatus(AuditDTO auditDTO) {
        // ID不能为空
        if (Objects.isNull(auditDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        // 审核失败必须填写说明
        if (AuthenticationStatusEnum.FAIL == auditDTO.getStatus() && StrUtil.isBlank(auditDTO.getRemark())) {
            throw new BusinessException("审核失败必须说明原因");
        }

        PersonalAuthenticationDO personalAuthentication = personalAuthenticationRepository.findById(auditDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 设置审核状态
        personalAuthentication.setAuthenticationStatus(auditDTO.getStatus());
        personalAuthentication.setRemark(auditDTO.getRemark());
        // 更新审核信息
        this.updateAuthenticationStatus(personalAuthentication);
        // 转换对象
        return PersonalAuthenticationConverter.convertTo(personalAuthentication);
    }


    /**
     * 进行实名认证
     *
     * @param personalAuthentication 认证信息
     * @return
     */
    private void updateAuthenticationStatus(PersonalAuthenticationDO personalAuthentication) {
        // 尝试校验用户实名信息
        HttpResult<Object> certificationResult = certificationService.certification(CertificationBody.builder()
                .userName(personalAuthentication.getUserName())
                .idCard(personalAuthentication.getIdentityCard())
                .tel(personalAuthentication.getTel())
                .build());
        // 认证成功
        if (HttpResultEnum.SUCCESS.getCode() == Integer.valueOf(certificationResult.getCode().toString())) {
            // 认证通过
            personalAuthentication.setAuthenticationStatus(AuthenticationStatusEnum.SUCCESS);
            // 标记已认证
            personalAuthentication.setAuthenticated(true);
            // 认证成功，则加入用户认证信息
            userAuthenticationService.addOrUpdate(UserAuthenticationDTO.builder()
                    .authenticationId(personalAuthentication.getId())
                    .authenticationType(AuthenticationTypeEnum.Personal)
                    .authenticationStatus(AuthenticationStatusEnum.SUCCESS)
                    .build());
        } else {
            // 认证失败
            personalAuthentication.setAuthenticationStatus(AuthenticationStatusEnum.FAIL);
            // 标记认证失败理由
            personalAuthentication.setRemark(certificationResult.getMessage());
        }
        // 添加审核记录
        authenticationRecordService.save(AuthenticationRecordDTO.builder()
                .authenticationId(personalAuthentication.getId())
                .type(AuthenticationTypeEnum.Personal)
                .status(personalAuthentication.getAuthenticationStatus())
                .remark(personalAuthentication.getRemark())
                .build());
        // 再次存储
        personalAuthenticationRepository.save(personalAuthentication);
        // 实名认证通知
        this.personalAuthenticationNotice(personalAuthentication);
    }

    /**
     * 实名认证通知 TODO 是否启用异步通知 @Async
     * <p>认证成功可操作逻辑：修改当前用户真实名字、注册会员等等</p>
     *
     * @param personalAuthentication
     */
    public void personalAuthenticationNotice(PersonalAuthenticationDO personalAuthentication) {
        // 审核回调
        try {
            // 尝试获取企业审核Bean
            IPersonalAuthenticationAdapter personalAuthenticationAdapter = SpringContextHolder.getBean(IPersonalAuthenticationAdapter.class);
            if (Objects.nonNull(personalAuthenticationAdapter)) {
                // 审核成功
                if (AuthenticationStatusEnum.SUCCESS == personalAuthentication.getAuthenticationStatus()) {
                    personalAuthenticationAdapter.authenticationSuccess(SecurityUtils.getAuthenticationUser().getId());
                }
                // 审核失败
                if (AuthenticationStatusEnum.FAIL == personalAuthentication.getAuthenticationStatus()) {
                    personalAuthenticationAdapter.authenticationFail(SecurityUtils.getAuthenticationUser().getId(), personalAuthentication.getRemark());
                }
            } else {
                log.info("当前未配置个人审核回调适配器");
            }
        } catch (Exception exception) {
            log.warn("个人资质审核回调异常：{}", exception.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String personalAuthenticationId) {
        // TODO 业务逻辑校验

        // 删除
        personalAuthenticationRepository.deleteById(personalAuthenticationId);
        return true;
    }

    @Override
    public PersonalAuthenticationVO detailById(String personalAuthenticationId) {
        // TODO 业务逻辑校验

        // 删除
        PersonalAuthenticationDO personalAuthentication = personalAuthenticationRepository.findById(personalAuthenticationId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return PersonalAuthenticationConverter.convertTo(personalAuthentication);
    }

    @Override
    public List<PersonalAuthenticationVO> list() {
        // 获取列表数据
        List<PersonalAuthenticationDO> personalAuthentications = personalAuthenticationRepository.findAll();
        return PersonalAuthenticationConverter.convertTo(personalAuthentications);
    }

    @Override
    public Page<PersonalAuthenticationVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return PersonalAuthenticationConverter.convertTo(personalAuthenticationRepository.findAll(pageable));
    }
}