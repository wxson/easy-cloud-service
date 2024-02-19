package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.api.dto.PersonalAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import com.easy.cloud.web.module.certification.api.vo.PersonalAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.adapter.IPersonalAuthenticationAdapter;
import com.easy.cloud.web.module.certification.biz.converter.PersonalAuthenticationConverter;
import com.easy.cloud.web.module.certification.biz.domain.PersonalAuthenticationDO;
import com.easy.cloud.web.module.certification.biz.repository.PersonalAuthenticationRepository;
import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
import com.easy.cloud.web.module.certification.biz.service.IPersonalAuthenticationService;
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PersonalAuthenticationVO submit(PersonalAuthenticationDTO personalAuthenticationDTO) {
        // 转换成DO对象
        PersonalAuthenticationDO personalAuthentication = PersonalAuthenticationConverter.convertTo(personalAuthenticationDTO);
        // TODO 校验逻辑

        // 存储
        personalAuthenticationRepository.save(personalAuthentication);
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

        PersonalAuthenticationDO personalAuthentication = personalAuthenticationRepository.findById(personalAuthenticationDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(personalAuthenticationDTO, personalAuthentication, true);
        // TODO 业务逻辑校验

        // 更新
        personalAuthenticationRepository.save(personalAuthentication);
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
        personalAuthentication.setStatus(auditDTO.getStatus());
        personalAuthentication.setRemark(auditDTO.getRemark());
        // 添加审核记录
        authenticationRecordService.save(AuthenticationRecordDTO.builder()
                .authenticationId(personalAuthentication.getId())
                .type(AuthenticationTypeEnum.Company)
                .status(auditDTO.getStatus())
                .remark(auditDTO.getRemark())
                .build());
        // 审核回调
        try {
            // 尝试获取企业审核Bean
            IPersonalAuthenticationAdapter personalAuthenticationAdapter = SpringContextHolder.getBean(IPersonalAuthenticationAdapter.class);
            if (Objects.nonNull(personalAuthenticationAdapter)) {
                // 审核成功
                if (AuthenticationStatusEnum.SUCCESS == auditDTO.getStatus()) {
                    personalAuthenticationAdapter.authenticationSuccess(SecurityUtils.getAuthenticationUser().getId());
                }
                // 审核失败
                if (AuthenticationStatusEnum.FAIL == auditDTO.getStatus()) {
                    personalAuthenticationAdapter.authenticationFail(SecurityUtils.getAuthenticationUser().getId());
                }
            } else {
                log.info("当前未配置个人审核回调适配器");
            }
        } catch (Exception exception) {
            log.warn("个人资质审核回调异常：{}", exception.getMessage());
        }

        return PersonalAuthenticationConverter.convertTo(personalAuthentication);
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