package com.easy.cloud.web.service.order.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.service.order.api.dto.InvoiceRecordDTO;
import com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO;
import com.easy.cloud.web.service.order.biz.converter.InvoiceRecordConverter;
import com.easy.cloud.web.service.order.biz.domain.InvoiceRecordDO;
import com.easy.cloud.web.service.order.biz.repository.InvoiceRecordRepository;
import com.easy.cloud.web.service.order.biz.service.IInvoiceRecordService;
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
 * InvoiceRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
@Slf4j
@Service
public class InvoiceRecordServiceImpl implements IInvoiceRecordService {

    @Autowired
    private InvoiceRecordRepository invoiceRecordRepository;

    @Override
    @Transactional
    public InvoiceRecordVO save(InvoiceRecordDTO invoiceRecordDTO) {
        // 转换成DO对象
        InvoiceRecordDO invoiceRecord = InvoiceRecordConverter.convertTo(invoiceRecordDTO);
        // TODO 校验逻辑

        // 存储
        invoiceRecordRepository.save(invoiceRecord);
        // 转换对象
        return InvoiceRecordConverter.convertTo(invoiceRecord);
    }

    @Override
    @Transactional
    public InvoiceRecordVO update(InvoiceRecordDTO invoiceRecordDTO) {
        // 转换成DO对象
        InvoiceRecordDO invoiceRecord = InvoiceRecordConverter.convertTo(invoiceRecordDTO);
        if (Objects.isNull(invoiceRecord.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        invoiceRecordRepository.save(invoiceRecord);
        // 转换对象
        return InvoiceRecordConverter.convertTo(invoiceRecord);
    }

    @Override
    @Transactional
    public Boolean removeById(String invoiceRecordId) {
        // TODO 业务逻辑校验

        // 删除
        invoiceRecordRepository.deleteById(invoiceRecordId);
        return true;
    }

    @Override
    public InvoiceRecordVO detailById(String invoiceRecordId) {
        // TODO 业务逻辑校验

        // 删除
        InvoiceRecordDO invoiceRecord = invoiceRecordRepository.findById(invoiceRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return InvoiceRecordConverter.convertTo(invoiceRecord);
    }

    @Override
    public List<InvoiceRecordVO> list() {
        // 获取列表数据
        List<InvoiceRecordDO> invoiceRecords = invoiceRecordRepository.findAll();
        return InvoiceRecordConverter.convertTo(invoiceRecords);
    }

    @Override
    public Page<InvoiceRecordVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return InvoiceRecordConverter.convertTo(invoiceRecordRepository.findAll(pageable));
    }
}