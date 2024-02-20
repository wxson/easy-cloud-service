package com.easy.cloud.web.service.member.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.service.member.api.dto.MemberLevelRecordDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO;
import com.easy.cloud.web.service.member.biz.converter.MemberLevelRecordConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberLevelRecordDO;
import com.easy.cloud.web.service.member.biz.repository.MemberLevelRecordRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberLevelRecordService;
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
 * MemberLevelRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
@Slf4j
@Service
public class MemberLevelRecordServiceImpl implements IMemberLevelRecordService {

    @Autowired
    private MemberLevelRecordRepository memberLevelRecordRepository;

    @Override
    @Transactional
    public MemberLevelRecordVO save(MemberLevelRecordDTO memberLevelRecordDTO) {
        // 转换成DO对象
        MemberLevelRecordDO memberLevelRecord = MemberLevelRecordConverter.convertTo(memberLevelRecordDTO);
        // TODO 校验逻辑

        // 存储
        memberLevelRecordRepository.save(memberLevelRecord);
        // 转换对象
        return MemberLevelRecordConverter.convertTo(memberLevelRecord);
    }

    @Override
    @Transactional
    public MemberLevelRecordVO update(MemberLevelRecordDTO memberLevelRecordDTO) {
        // 转换成DO对象
        MemberLevelRecordDO memberLevelRecord = MemberLevelRecordConverter.convertTo(memberLevelRecordDTO);
        if (Objects.isNull(memberLevelRecord.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        memberLevelRecordRepository.save(memberLevelRecord);
        // 转换对象
        return MemberLevelRecordConverter.convertTo(memberLevelRecord);
    }

    @Override
    @Transactional
    public Boolean removeById(String memberLevelRecordId) {
        // TODO 业务逻辑校验

        // 删除
        memberLevelRecordRepository.deleteById(memberLevelRecordId);
        return true;
    }

    @Override
    public MemberLevelRecordVO detailById(String memberLevelRecordId) {
        // TODO 业务逻辑校验

        // 删除
        MemberLevelRecordDO memberLevelRecord = memberLevelRecordRepository.findById(memberLevelRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return MemberLevelRecordConverter.convertTo(memberLevelRecord);
    }

    @Override
    public List<MemberLevelRecordVO> list(String userId) {
        // 获取列表数据
        List<MemberLevelRecordDO> memberLevelRecords = memberLevelRecordRepository.findAllByUserIdOrderByCreateAt(userId);
        return MemberLevelRecordConverter.convertTo(memberLevelRecords);
    }

    @Override
    public Page<MemberLevelRecordVO> page(String userId, int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return MemberLevelRecordConverter.convertTo(memberLevelRecordRepository.findAllByUserIdOrderByCreateAt(userId, pageable));
    }
}