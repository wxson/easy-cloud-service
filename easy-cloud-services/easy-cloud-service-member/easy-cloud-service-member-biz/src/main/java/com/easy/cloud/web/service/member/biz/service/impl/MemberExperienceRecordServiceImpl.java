package com.easy.cloud.web.service.member.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.service.member.api.dto.MemberExperienceRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberExperienceRecordVO;
import com.easy.cloud.web.service.member.biz.converter.MemberExperienceRecordConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberExperienceRecordDO;
import com.easy.cloud.web.service.member.biz.repository.MemberExperienceRecordRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberExperienceRecordService;
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
 * MemberExperienceRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
@Slf4j
@Service
public class MemberExperienceRecordServiceImpl implements IMemberExperienceRecordService {

    @Autowired
    private MemberExperienceRecordRepository memberExperienceRecordRepository;

    @Override
    @Transactional
    public MemberExperienceRecordVO save(MemberExperienceRecordDTO memberExperienceRecordDTO) {
        // 转换成DO对象
        MemberExperienceRecordDO memberExperienceRecord = MemberExperienceRecordConverter.convertTo(memberExperienceRecordDTO);
        // TODO 校验逻辑

        // 存储
        memberExperienceRecordRepository.save(memberExperienceRecord);
        // 转换对象
        return MemberExperienceRecordConverter.convertTo(memberExperienceRecord);
    }

    @Override
    @Transactional
    public MemberExperienceRecordVO update(MemberExperienceRecordDTO memberExperienceRecordDTO) {
        // 转换成DO对象
        MemberExperienceRecordDO memberExperienceRecord = MemberExperienceRecordConverter.convertTo(memberExperienceRecordDTO);
        if (Objects.isNull(memberExperienceRecord.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        memberExperienceRecordRepository.save(memberExperienceRecord);
        // 转换对象
        return MemberExperienceRecordConverter.convertTo(memberExperienceRecord);
    }

    @Override
    @Transactional
    public Boolean removeById(String memberExperienceRecordId) {
        // TODO 业务逻辑校验

        // 删除
        memberExperienceRecordRepository.deleteById(memberExperienceRecordId);
        return true;
    }

    @Override
    public MemberExperienceRecordVO detailById(String memberExperienceRecordId) {
        // TODO 业务逻辑校验

        // 删除
        MemberExperienceRecordDO memberExperienceRecord = memberExperienceRecordRepository.findById(memberExperienceRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return MemberExperienceRecordConverter.convertTo(memberExperienceRecord);
    }

    @Override
    public List<MemberExperienceRecordVO> list(String userId) {
        // 获取列表数据
        List<MemberExperienceRecordDO> memberExperienceRecords = memberExperienceRecordRepository.findAllByUserIdOrderByCreateAt(userId);
        return MemberExperienceRecordConverter.convertTo(memberExperienceRecords);
    }

    @Override
    public Page<MemberExperienceRecordVO> page(String userId, int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return MemberExperienceRecordConverter.convertTo(memberExperienceRecordRepository.findAllByUserIdOrderByCreateAt(userId, pageable));
    }
}