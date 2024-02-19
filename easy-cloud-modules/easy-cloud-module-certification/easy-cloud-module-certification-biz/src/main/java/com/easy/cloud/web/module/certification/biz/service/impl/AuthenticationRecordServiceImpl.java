package com.easy.cloud.web.module.certification.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.certification.api.dto.AuthenticationRecordDTO;
import com.easy.cloud.web.module.certification.api.vo.AuthenticationRecordVO;
import com.easy.cloud.web.module.certification.biz.converter.AuthenticationRecordConverter;
import com.easy.cloud.web.module.certification.biz.domain.AuthenticationRecordDO;
import com.easy.cloud.web.module.certification.biz.repository.AuthenticationRecordRepository;
import com.easy.cloud.web.module.certification.biz.service.IAuthenticationRecordService;
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
 * AuthenticationRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
@Slf4j
@Service
public class AuthenticationRecordServiceImpl implements IAuthenticationRecordService {

    @Autowired
    private AuthenticationRecordRepository authenticationRecordRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AuthenticationRecordVO save(AuthenticationRecordDTO authenticationRecordDTO) {
        // 转换成DO对象
        AuthenticationRecordDO authenticationRecord = AuthenticationRecordConverter.convertTo(authenticationRecordDTO);
        // TODO 校验逻辑

        // 存储
        authenticationRecordRepository.save(authenticationRecord);
        // 转换对象
        return AuthenticationRecordConverter.convertTo(authenticationRecord);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AuthenticationRecordVO update(AuthenticationRecordDTO authenticationRecordDTO) {
        // ID不能为空
        if (Objects.isNull(authenticationRecordDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        AuthenticationRecordDO authenticationRecord = authenticationRecordRepository.findById(authenticationRecordDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(authenticationRecordDTO, authenticationRecord, true);
        // TODO 业务逻辑校验

        // 更新
        authenticationRecordRepository.save(authenticationRecord);
        // 转换对象
        return AuthenticationRecordConverter.convertTo(authenticationRecord);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String authenticationRecordId) {
        // TODO 业务逻辑校验

        // 删除
        authenticationRecordRepository.deleteById(authenticationRecordId);
        return true;
    }

    @Override
    public AuthenticationRecordVO detailById(String authenticationRecordId) {
        // TODO 业务逻辑校验

        // 删除
        AuthenticationRecordDO authenticationRecord = authenticationRecordRepository.findById(authenticationRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return AuthenticationRecordConverter.convertTo(authenticationRecord);
    }

    @Override
    public List<AuthenticationRecordVO> list() {
        // 获取列表数据
        List<AuthenticationRecordDO> authenticationRecords = authenticationRecordRepository.findAll();
        return AuthenticationRecordConverter.convertTo(authenticationRecords);
    }

    @Override
    public Page<AuthenticationRecordVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(page, size);
        return AuthenticationRecordConverter.convertTo(authenticationRecordRepository.findAll(pageable));
    }
}