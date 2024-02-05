package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.service.order.api.dto.InvoiceDTO;
import com.easy.cloud.web.service.order.biz.domain.InvoiceDO;
import com.easy.cloud.web.service.order.api.vo.InvoiceVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Invoice转换器
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
public class InvoiceConverter {

    /**
     * DTO转为DO
     *
     * @param invoice 转换数据
     * @return com.easy.cloud.web.service.order.biz.domain.InvoiceDO
     */
    public static InvoiceDO convertTo(InvoiceDTO invoice){
        return InvoiceDO.builder()
                .id(invoice.getId())
                .bankAccount(invoice.getBankAccount())
                .address(invoice.getAddress())
                .province(invoice.getProvince())
                .depositBank(invoice.getDepositBank())
                .city(invoice.getCity())
                .street(invoice.getStreet())
                .district(invoice.getDistrict())
                .header(invoice.getHeader())
                .tel(invoice.getTel())
                .type(invoice.getType())
                .taxNo(invoice.getTaxNo())
                .build();
    }

    /**
     * DO转为VO
     *
     * @param invoice 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceVO
     */
    public static InvoiceVO convertTo(InvoiceDO invoice){
        return InvoiceVO.builder()
                .id(invoice.getId())
                .creatorAt(invoice.getCreatorAt())
                .createAt(invoice.getCreateAt())
                .updateAt(invoice.getUpdateAt())
                .bankAccount(invoice.getBankAccount())
                .address(invoice.getAddress())
                .province(invoice.getProvince())
                .depositBank(invoice.getDepositBank())
                .city(invoice.getCity())
                .street(invoice.getStreet())
                .district(invoice.getDistrict())
                .header(invoice.getHeader())
                .tel(invoice.getTel())
                .type(invoice.getType())
                .taxNo(invoice.getTaxNo())
                .build();
    }

    /**
     * 列表DO转为VO
     *
     * @param invoices 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceVO
     */
    public static List<InvoiceVO> convertTo(List<InvoiceDO> invoices){
        return invoices.stream()
                .map(InvoiceConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceVO
     */
    public static Page<InvoiceVO> convertTo(Page<InvoiceDO> page){
        return page.map(InvoiceConverter::convertTo);
    }
}