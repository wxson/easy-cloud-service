package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.CreditCodeUtil;
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
import com.easy.cloud.web.module.certification.api.dto.CompanyAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.dto.UserAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import com.easy.cloud.web.module.certification.api.vo.CompanyAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.adapter.ICompanyAuthenticationAdapter;
import com.easy.cloud.web.module.certification.biz.converter.CompanyAuthenticationConverter;
import com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO;
import com.easy.cloud.web.module.certification.biz.repository.CompanyAuthenticationRepository;
import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
import com.easy.cloud.web.module.certification.biz.service.ICertificationService;
import com.easy.cloud.web.module.certification.biz.service.ICompanyAuthenticationService;
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
import java.util.Optional;

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

    @Autowired
    private ICertificationService certificationService;

    @Autowired
    private IUserAuthenticationService userAuthenticationService;

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

        // 校验统一社会信用代码格式
        if (StrUtil.isNotBlank(companyAuthenticationDTO.getUsci())
                && !CreditCodeUtil.isCreditCode(companyAuthenticationDTO.getUsci())) {
            throw new BusinessException("当前社会信用代码格式不正确");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CompanyAuthenticationVO submit(CompanyAuthenticationDTO companyAuthenticationDTO) {
        // 校验
        this.authenticationBeforeValid(companyAuthenticationDTO);

        // 校验是否重复认证
        Optional<CompanyAuthenticationDO> companyAuthenticationOptional = companyAuthenticationRepository.findByUsci(companyAuthenticationDTO.getUsci());
        // 如果存在，则禁止二次提交，可直接修改上一次提交的类容
        if (companyAuthenticationOptional.isPresent()) {
            CompanyAuthenticationDO companyAuthenticationDO = companyAuthenticationOptional.get();
            // 认证失败，不得再次提交认证，认证表中必须唯一
            if (AuthenticationStatusEnum.FAIL == companyAuthenticationDO.getAuthenticationStatus()) {
                throw new BusinessException("当前企业认证资料已存在，请前往认证详情进行修改");
            }

            throw new BusinessException("当前企业已认证，请勿重复认证");
        }

        // 转换成DO对象
        CompanyAuthenticationDO companyAuthentication = CompanyAuthenticationConverter.convertTo(companyAuthenticationDTO);
        // TODO 校验逻辑

        // 设置账号ID
        companyAuthentication.setUserId(SecurityUtils.getAuthenticationUser().getId());
        // 存储
        companyAuthenticationRepository.save(companyAuthentication);
        // 更新审核信息
        this.updateAuthenticationStatus(companyAuthentication);
        // 转换对象
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CompanyAuthenticationVO update(CompanyAuthenticationDTO companyAuthenticationDTO) {
        // 转换成DO对象
        if (Objects.isNull(companyAuthenticationDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        // 校验
        this.authenticationBeforeValid(companyAuthenticationDTO);

        CompanyAuthenticationDO companyAuthentication = companyAuthenticationRepository.findById(companyAuthenticationDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(companyAuthenticationDTO, companyAuthentication, true);
        // TODO 业务逻辑校验

        // 认证信息一旦更新，则重置当前认证信息为待审核、未认证
        companyAuthentication.setAuthenticationStatus(AuthenticationStatusEnum.WAIT);
        companyAuthentication.setAuthenticated(false);
        // 设置账号ID
        companyAuthentication.setUserId(SecurityUtils.getAuthenticationUser().getId());
        // 更新审核信息
        this.updateAuthenticationStatus(companyAuthentication);
        // 转换对象
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CompanyAuthenticationVO updateAuthenticationStatus(AuditDTO auditDTO) {
        // ID不能为空
        if (Objects.isNull(auditDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        // 审核失败必须填写说明
        if (AuthenticationStatusEnum.FAIL == auditDTO.getStatus() && StrUtil.isBlank(auditDTO.getRemark())) {
            throw new BusinessException("审核失败必须说明原因");
        }

        // 获取企业审核认证
        CompanyAuthenticationDO companyAuthentication = companyAuthenticationRepository.findById(auditDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 设置审核状态
        companyAuthentication.setAuthenticationStatus(auditDTO.getStatus());
        companyAuthentication.setRemark(auditDTO.getRemark());
        companyAuthenticationRepository.save(companyAuthentication);

        // 添加企业认证信息
        userAuthenticationService.addOrUpdate(UserAuthenticationDTO.builder()
                // 必填
                .userId(companyAuthentication.getUserId())
                .authenticationId(companyAuthentication.getId())
                .authenticationType(AuthenticationTypeEnum.Company)
                .authenticationStatus(auditDTO.getStatus())
                .remark(auditDTO.getRemark())
                .build());
        return CompanyAuthenticationConverter.convertTo(companyAuthentication);
    }


    /**
     * 进行企业认证
     *
     * @param companyAuthentication 认证信息
     * @return
     */
    private void updateAuthenticationStatus(CompanyAuthenticationDO companyAuthentication) {
        // 尝试校验用户实名信息
        HttpResult<Object> certificationResult = certificationService.certification(CertificationBody.builder()
                .userName(companyAuthentication.getLegalPerson())
                .idCard(companyAuthentication.getLegalPersonIdentityCard())
                .tel(companyAuthentication.getTel())
                .build());
        // 认证成功
        if (HttpResultEnum.SUCCESS.getCode() == Integer.valueOf(certificationResult.getCode().toString())) {
            // 标记已认证，企业审核需人工二次审核方可完成
            companyAuthentication.setAuthenticated(true);
        } else {
            // 认证失败
            companyAuthentication.setAuthenticationStatus(AuthenticationStatusEnum.FAIL);
            // 标记认证失败理由
            companyAuthentication.setRemark(certificationResult.getMessage());
        }
        
        // 添加审核记录
        authenticationRecordService.save(AuthenticationRecordDTO.builder()
                .authenticationId(companyAuthentication.getId())
                .type(AuthenticationTypeEnum.Company)
                .status(companyAuthentication.getAuthenticationStatus())
                .remark(companyAuthentication.getRemark())
                .build());
        // 再次存储
        companyAuthenticationRepository.save(companyAuthentication);

        // 实名认证通知
        this.companyAuthenticationNotice(companyAuthentication);
    }

    /**
     * 企业认证通知 TODO 是否启用异步通知 @Async
     * <p>认证成功可操作逻辑：修改当前用户真实名字、注册会员等等</p>
     *
     * @param companyAuthentication
     */
    public void companyAuthenticationNotice(CompanyAuthenticationDO companyAuthentication) {
        // 审核回调
        try {
            // 尝试获取企业审核Bean
            ICompanyAuthenticationAdapter companyAuthenticationAdapter = SpringContextHolder.getBean(ICompanyAuthenticationAdapter.class);
            if (Objects.nonNull(companyAuthenticationAdapter)) {
                // 审核成功
                if (AuthenticationStatusEnum.SUCCESS == companyAuthentication.getAuthenticationStatus()) {
                    companyAuthenticationAdapter.authenticationSuccess(SecurityUtils.getAuthenticationUser().getId());
                }
                // 审核失败
                if (AuthenticationStatusEnum.FAIL == companyAuthentication.getAuthenticationStatus()) {
                    companyAuthenticationAdapter.authenticationFail(SecurityUtils.getAuthenticationUser().getId(), companyAuthentication.getRemark());
                }
            } else {
                log.info("当前未配置企业审核回调适配器");
            }
        } catch (Exception exception) {
            log.warn("企业资质审核回调异常：{}", exception.getMessage());
        }
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