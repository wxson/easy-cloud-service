package com.easy.cloud.web.service.order.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.service.order.api.dto.InvoiceDTO;
import com.easy.cloud.web.service.order.api.vo.InvoiceVO;
import com.easy.cloud.web.service.order.biz.converter.InvoiceConverter;
import com.easy.cloud.web.service.order.biz.domain.InvoiceDO;
import com.easy.cloud.web.service.order.biz.repository.InvoiceRepository;
import com.easy.cloud.web.service.order.biz.service.IInvoiceService;
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
 * Invoice 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
@Slf4j
@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public InvoiceVO save(InvoiceDTO invoiceDTO) {
        // 转换成DO对象
        InvoiceDO invoice = InvoiceConverter.convertTo(invoiceDTO);
        // TODO 校验逻辑

        // 存储
        invoiceRepository.save(invoice);
        // 转换对象
        return InvoiceConverter.convertTo(invoice);
    }

    @Override
    @Transactional
    public InvoiceVO update(InvoiceDTO invoiceDTO) {
        // 转换成DO对象
        InvoiceDO invoice = InvoiceConverter.convertTo(invoiceDTO);
        if (Objects.isNull(invoice.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        invoiceRepository.save(invoice);
        // 转换对象
        return InvoiceConverter.convertTo(invoice);
    }

    @Override
    @Transactional
    public Boolean removeById(String invoiceId) {
        // TODO 业务逻辑校验

        // 删除
        invoiceRepository.deleteById(invoiceId);
        return true;
    }

    @Override
    public InvoiceVO detailById(String invoiceId) {
        // TODO 业务逻辑校验

        // 删除
        InvoiceDO invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return InvoiceConverter.convertTo(invoice);
    }

    @Override
    public List<InvoiceVO> list() {
        // 获取列表数据
        List<InvoiceDO> invoices = invoiceRepository.findAll();
        return InvoiceConverter.convertTo(invoices);
    }

    @Override
    public Page<InvoiceVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return InvoiceConverter.convertTo(invoiceRepository.findAll(pageable));
    }
}