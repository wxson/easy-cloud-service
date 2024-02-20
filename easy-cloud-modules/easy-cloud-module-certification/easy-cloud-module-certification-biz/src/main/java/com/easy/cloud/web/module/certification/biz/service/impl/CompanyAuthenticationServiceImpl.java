package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.module.certification.api.dto.AuditDTO;
import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.api.dto.CompanyAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.adapter.ICompanyAuthenticationAdapter;
import com.easy.cloud.web.module.certification.biz.converter.CompanyAuthenticationConverter;
import com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO;
import com.easy.cloud.web.module.certification.biz.repository.CompanyAuthenticationRepository;
import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
import com.easy.cloud.web.module.certification.biz.service.ICompanyAuthenticationService;
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
 * CompanyAuthentication 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Slf4j
@Service
public class CompanyAuthenticationServiceImpl implements ICompanyAuthenticationService {

    @Autowired
    private CompanyAuthenticationRepository companyAuthenticationRepository;

    @Autowired
    private IAuthenticationRecordService authenticationRecordService;

    /**
     * 认证之前校验
     *
     * @param companyAuthenticationDTO 认证参数
     */
    private void authenticationBeforeValid(CompanyAuthenticationDTO companyAuthenticationDTO) {
        // 手机号校验
        if (StrUtil.isNotBlank(companyAuthenticationDTO.getTel())
                && !PhoneUtil.isPhone(companyAuthenticationDTO.getTel())) {
            throw new BusinessException("当前手机号格式不正确");
        }

        // 身份证验证
        if (StrUtil.isNotBlank(companyAuthenticationDTO.getLegalPersonIdentityCard())
                && !IdcardUtil.isValidCard18(companyAuthenticationDTO.getLegalPersonIdentityCard())) {
            throw new BusinessException("当前身份证格式不正确");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CompanyAuthenticationVO submit(CompanyAuthenticationDTO companyAuthenticationDTO) {
        // 校验
        this.authenticationBeforeValid(companyAuthenticationDTO);

        // 转换成DO对象
        CompanyAuthenticationDO companyAuthentication = CompanyAuthenticationConverter.convertTo(companyAuthenticationDTO);
        // TODO 校验逻辑

        // 存储
        companyAuthenticationRepository.save(companyAuthentication);
        // 转换对象
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CompanyAuthenticationVO update(CompanyAuthenticationDTO companyAuthenticationDTO) {
        // 校验
        this.authenticationBeforeValid(companyAuthenticationDTO);

        // 转换成DO对象
        if (Objects.isNull(companyAuthenticationDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        CompanyAuthenticationDO companyAuthentication = companyAuthenticationRepository.findById(companyAuthenticationDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(companyAuthenticationDTO, companyAuthentication, true);
        // TODO 业务逻辑校验

        // 更新
        companyAuthenticationRepository.save(companyAuthentication);
        // 转换对象
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    public CompanyAuthenticationVO updateAuthenticationStatus(AuditDTO auditDTO) {
        // ID不能为空
        if (Objects.isNull(auditDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        // 审核失败必须填写说明
        if (AuthenticationStatusEnum.FAIL == auditDTO.getStatus() && StrUtil.isBlank(auditDTO.getRemark())) {
            throw new BusinessException("审核失败必须说明原因");
        }

        CompanyAuthenticationDO companyAuthentication = companyAuthenticationRepository.findById(auditDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 设置审核状态
        companyAuthentication.setStatus(auditDTO.getStatus());
        companyAuthentication.setRemark(auditDTO.getRemark());
        // 添加审核记录
        authenticationRecordService.save(AuthenticationRecordDTO.builder()
                .authenticationId(companyAuthentication.getId())
                .type(AuthenticationTypeEnum.Company)
                .status(auditDTO.getStatus())
                .remark(auditDTO.getRemark())
                .build());
        // 审核回调
        try {
            // 尝试获取企业审核Bean
            ICompanyAuthenticationAdapter companyAuthenticationAdapter = SpringContextHolder.getBean(ICompanyAuthenticationAdapter.class);
            if (Objects.nonNull(companyAuthenticationAdapter)) {
                // 审核成功
                if (AuthenticationStatusEnum.SUCCESS == auditDTO.getStatus()) {
                    companyAuthenticationAdapter.authenticationSuccess(SecurityUtils.getAuthenticationUser().getId());
                }
                // 审核失败
                if (AuthenticationStatusEnum.FAIL == auditDTO.getStatus()) {
                    companyAuthenticationAdapter.authenticationFail(SecurityUtils.getAuthenticationUser().getId());
                }
            } else {
                log.info("当前未配置企业审核回调适配器");
            }
        } catch (Exception exception) {
            log.warn("企业资质审核回调异常：{}", exception.getMessage());
        }

        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String companyAuthenticationId) {
        // TODO 业务逻辑校验

        // 删除
        companyAuthenticationRepository.deleteById(companyAuthenticationId);
        return true;
    }

    @Override
    public CompanyAuthenticationVO detailById(String companyAuthenticationId) {
        // TODO 业务逻辑校验

        // 删除
        CompanyAuthenticationDO companyAuthentication = companyAuthenticationRepository.findById(companyAuthenticationId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    public List<CompanyAuthenticationVO> list() {
        // 获取列表数据
        List<CompanyAuthenticationDO> companyAuthentications = companyAuthenticationRepository.findAll();
        return CompanyAuthenticationConverter.convertTo(companyAuthentications);
    }

    @Override
    public Page<CompanyAuthenticationVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return CompanyAuthenticationConverter.convertTo(companyAuthenticationRepository.findAll(pageable));
    }
}