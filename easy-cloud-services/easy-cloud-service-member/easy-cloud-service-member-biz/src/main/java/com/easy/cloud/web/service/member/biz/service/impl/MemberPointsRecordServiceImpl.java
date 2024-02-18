package com.easy.cloud.web.service.member.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.service.member.api.dto.MemberPointsRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO;
import com.easy.cloud.web.service.member.biz.converter.MemberPointsRecordConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberPointsRecordDO;
import com.easy.cloud.web.service.member.biz.repository.MemberPointsRecordRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberPointsRecordService;
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
 * MemberPointsRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
@Slf4j
@Service
public class MemberPointsRecordServiceImpl implements IMemberPointsRecordService {

    @Autowired
    private MemberPointsRecordRepository memberPointsRecordRepository;

    @Override
    @Transactional
    public MemberPointsRecordVO save(MemberPointsRecordDTO memberPointsRecordDTO) {
        // 转换成DO对象
        MemberPointsRecordDO memberPointsRecord = MemberPointsRecordConverter.convertTo(memberPointsRecordDTO);
        // TODO 校验逻辑

        // 存储
        memberPointsRecordRepository.save(memberPointsRecord);
        // 转换对象
        return MemberPointsRecordConverter.convertTo(memberPointsRecord);
    }

    @Override
    @Transactional
    public MemberPointsRecordVO update(MemberPointsRecordDTO memberPointsRecordDTO) {
        // 转换成DO对象
        MemberPointsRecordDO memberPointsRecord = MemberPointsRecordConverter.convertTo(memberPointsRecordDTO);
        if (Objects.isNull(memberPointsRecord.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        memberPointsRecordRepository.save(memberPointsRecord);
        // 转换对象
        return MemberPointsRecordConverter.convertTo(memberPointsRecord);
    }

    @Override
    @Transactional
    public Boolean removeById(String memberPointsRecordId) {
        // TODO 业务逻辑校验

        // 删除
        memberPointsRecordRepository.deleteById(memberPointsRecordId);
        return true;
    }

    @Override
    public MemberPointsRecordVO detailById(String memberPointsRecordId) {
        // TODO 业务逻辑校验

        // 删除
        MemberPointsRecordDO memberPointsRecord = memberPointsRecordRepository.findById(memberPointsRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return MemberPointsRecordConverter.convertTo(memberPointsRecord);
    }

    @Override
    public List<MemberPointsRecordVO> list(String userId) {
        // 获取列表数据
        List<MemberPointsRecordDO> memberPointsRecords = memberPointsRecordRepository.findAllByUserIdOrderByCreateAt(userId);
        return MemberPointsRecordConverter.convertTo(memberPointsRecords);
    }

    @Override
    public Page<MemberPointsRecordVO> page(String userId, int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return MemberPointsRecordConverter.convertTo(memberPointsRecordRepository.findAllByUserIdOrderByCreateAt(userId, pageable));
    }
}