package com.easy.cloud.web.service.member.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberLevelDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelVO;
import com.easy.cloud.web.service.member.biz.converter.MemberLevelConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberLevelDO;
import com.easy.cloud.web.service.member.biz.repository.MemberLevelRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberLevelService;
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
 * MemberLevel 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
@Slf4j
@Service
public class MemberLevelServiceImpl implements IMemberLevelService {

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public MemberLevelVO save(MemberLevelDTO memberLevelDTO) {
        // 转换成DO对象
        MemberLevelDO memberLevel = MemberLevelConverter.convertTo(memberLevelDTO);
        // TODO 校验逻辑

        // 存储
        memberLevelRepository.save(memberLevel);
        // 转换对象
        return MemberLevelConverter.convertTo(memberLevel);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public MemberLevelVO update(MemberLevelDTO memberLevelDTO) {
        // 转换成DO对象
        if (Objects.isNull(memberLevelDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        MemberLevelDO memberLevel = memberLevelRepository.findById(memberLevelDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(memberLevelDTO, memberLevel, true);

        // 更新
        memberLevelRepository.save(memberLevel);
        // 转换对象
        return MemberLevelConverter.convertTo(memberLevel);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String memberLevelId) {
        // TODO 业务逻辑校验

        // 删除
        memberLevelRepository.deleteById(memberLevelId);
        return true;
    }

    @Override
    public MemberLevelVO detailById(String memberLevelId) {
        // TODO 业务逻辑校验

        // 删除
        MemberLevelDO memberLevel = memberLevelRepository.findById(memberLevelId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return MemberLevelConverter.convertTo(memberLevel);
    }

    @Override
    public List<MemberLevelVO> list() {
        // 获取列表数据
        List<MemberLevelDO> memberLevels = memberLevelRepository.findAll();
        return MemberLevelConverter.convertTo(memberLevels);
    }

    @Override
    public Page<MemberLevelVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return MemberLevelConverter.convertTo(memberLevelRepository.findAll(pageable));
    }
}