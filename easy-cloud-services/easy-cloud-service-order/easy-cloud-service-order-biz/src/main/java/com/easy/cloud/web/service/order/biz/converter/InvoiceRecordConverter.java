package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.order.api.dto.InvoiceRecordDTO;
import com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO;
import com.easy.cloud.web.service.order.biz.domain.InvoiceRecordDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * InvoiceRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
public class InvoiceRecordConverter {

    /**
     * DTO转为DO
     *
     * @param invoiceRecord 转换数据
     * @return com.easy.cloud.web.service.order.biz.domain.InvoiceRecordDO
     */
    public static InvoiceRecordDO convertTo(InvoiceRecordDTO invoiceRecord) {
        InvoiceRecordDO invoiceRecordDO = InvoiceRecordDO.builder().build();
        BeanUtils.copyProperties(invoiceRecord, invoiceRecordDO, true);
        return invoiceRecordDO;
    }

    /**
     * DO转为VO
     *
     * @param invoiceRecord 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
    public static InvoiceRecordVO convertTo(InvoiceRecordDO invoiceRecord) {
        InvoiceRecordVO invoiceRecordVO = InvoiceRecordVO.builder().build();
        BeanUtils.copyProperties(invoiceRecord, invoiceRecordVO, true);
        return invoiceRecordVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param invoiceRecords 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
    public static List<InvoiceRecordVO> convertTo(List<InvoiceRecordDO> invoiceRecords) {
        return invoiceRecords.stream()
                .map(InvoiceRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
    public static Page<InvoiceRecordVO> convertTo(Page<InvoiceRecordDO> page) {
        return page.map(InvoiceRecordConverter::convertTo);
    }
}