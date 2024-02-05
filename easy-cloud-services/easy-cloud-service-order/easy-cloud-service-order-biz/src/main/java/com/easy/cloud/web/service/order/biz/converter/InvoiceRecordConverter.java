package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.service.order.api.dto.InvoiceRecordDTO;
import com.easy.cloud.web.service.order.biz.domain.InvoiceRecordDO;
import com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO;
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
    public static InvoiceRecordDO convertTo(InvoiceRecordDTO invoiceRecord){
        return InvoiceRecordDO.builder()
                .id(invoiceRecord.getId())
                .amount(invoiceRecord.getAmount())
                .orderNo(invoiceRecord.getOrderNo())
                .address(invoiceRecord.getAddress())
                .header(invoiceRecord.getHeader())
                .tel(invoiceRecord.getTel())
                .type(invoiceRecord.getType())
                .taxNo(invoiceRecord.getTaxNo())
                .goodsName(invoiceRecord.getGoodsName())
                .build();
    }

    /**
     * DO转为VO
     *
     * @param invoiceRecord 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
    public static InvoiceRecordVO convertTo(InvoiceRecordDO invoiceRecord){
        return InvoiceRecordVO.builder()
                .id(invoiceRecord.getId())
                .creatorAt(invoiceRecord.getCreatorAt())
                .createAt(invoiceRecord.getCreateAt())
                .updateAt(invoiceRecord.getUpdateAt())
                .amount(invoiceRecord.getAmount())
                .orderNo(invoiceRecord.getOrderNo())
                .address(invoiceRecord.getAddress())
                .header(invoiceRecord.getHeader())
                .tel(invoiceRecord.getTel())
                .type(invoiceRecord.getType())
                .taxNo(invoiceRecord.getTaxNo())
                .goodsName(invoiceRecord.getGoodsName())
                .build();
    }

    /**
     * 列表DO转为VO
     *
     * @param invoiceRecords 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
    public static List<InvoiceRecordVO> convertTo(List<InvoiceRecordDO> invoiceRecords){
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
    public static Page<InvoiceRecordVO> convertTo(Page<InvoiceRecordDO> page){
        return page.map(InvoiceRecordConverter::convertTo);
    }
}