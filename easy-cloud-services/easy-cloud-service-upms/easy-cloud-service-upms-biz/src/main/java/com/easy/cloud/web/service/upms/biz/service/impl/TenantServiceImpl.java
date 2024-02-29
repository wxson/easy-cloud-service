package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.DeptDTO;
import com.easy.cloud.web.service.upms.api.dto.TenantDTO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.enums.RoleEnum;
import com.easy.cloud.web.service.upms.api.vo.TenantVO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsConstants;
import com.easy.cloud.web.service.upms.biz.converter.TenantConverter;
import com.easy.cloud.web.service.upms.biz.domain.TenantDO;
import com.easy.cloud.web.service.upms.biz.repository.TenantRepository;
import com.easy.cloud.web.service.upms.biz.service.IDeptService;
import com.easy.cloud.web.service.upms.biz.service.ITenantService;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
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
 * dept 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Slf4j
@Service
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Override
    public void init() {
        // 未初始化过数据
        if (tenantRepository.count() <= 0) {

        }
    }

    @Override
    @Transactional
    public TenantVO save(TenantDTO tenantDTO) {
        // 转换成DO对象
        TenantDO tenant = TenantConverter.convertTo(tenantDTO);
        // TODO 校验逻辑

        // 存储
        tenantRepository.save(tenant);
        // 1、初始化租户的部门信息
        DeptDTO deptDTO = tenantDTO.convertTo(DeptDTO.class);
        // 绑定租户ID
        deptDTO.setTenantId(tenant.getId());
        deptService.save(deptDTO);
        // 2、构建租户管理员账号信息
        UserDTO userDTO = tenantDTO.convertTo(UserDTO.class);
        // 绑定租户ID
        userDTO.setTenantId(tenant.getId());
        userDTO.setRoleCodes(CollUtil.newHashSet(RoleEnum.ROLE_TENANT.getCode()));
        // TODO 设置租户密码，默认123456；并通过短信或邮箱进行通知
        userDTO.setUserName(tenantDTO.getName());
        if (StrUtil.isBlank(userDTO.getPassword())) {
            userDTO.setPassword(UpmsConstants.DEFAULT_PASSWORD);
        }
        // 存储租户登录用户信息
        UserVO userVO = userService.save(userDTO);
        // 绑定负责人ID
        tenant.setAccountId(userVO.getId());
        tenantRepository.save(tenant);
        // 转换对象
        return TenantConverter.convertTo(tenant);
    }

    @Override
    @Transactional
    public TenantVO update(TenantDTO tenantDTO) {
        // 更新操作，ID不能为空
        if (Objects.isNull(tenantDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // 获取当前部门信息
        TenantDO tenant = tenantRepository.findById(tenantDTO.getId())
                .orElseThrow(() -> new BusinessException("当前租户信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(tenantDTO, tenant, true);
        // 存储，更新租户信息不修改部门、用户等信息
        tenantRepository.save(tenant);
        // 转换对象
        return TenantConverter.convertTo(tenant);
    }

    @Override
    @Transactional
    public Boolean removeById(String tenantId) {
        // TODO 业务逻辑校验

        // 删除
        tenantRepository.logicDelete(tenantId);
        // 移除租户下的所有用户
        userService.removeTenantAllUser(tenantId);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TenantVO freezeTenant(String tenantId) {
        // TODO 业务逻辑校验
        TenantDO tenantDO = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new BusinessException("当前租户信息不存在"));
        // 删除
        tenantRepository.updateTenantStatus(tenantId, StatusEnum.FREEZE_STATUS);
        // 移除租户下的所有用户
        userService.freezeTenantAllUser(tenantId);
        return TenantConverter.convertTo(tenantDO);
    }

    @Override
    public TenantVO detailById(String tenantId) {
        // TODO 业务逻辑校验

        // 删除
        TenantDO tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return TenantConverter.convertTo(tenant);
    }

    @Override
    public List<TenantVO> list() {
        // 获取列表数据
        List<TenantDO> tenants = tenantRepository.findAll();
        return TenantConverter.convertTo(tenants);
    }

    @Override
    public Page<TenantVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        return TenantConverter.convertTo(tenantRepository.findAll(pageable));
    }
}