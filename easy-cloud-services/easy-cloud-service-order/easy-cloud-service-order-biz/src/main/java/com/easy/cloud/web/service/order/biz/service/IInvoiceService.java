package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.dto.InvoiceDTO;
import com.easy.cloud.web.service.order.api.vo.InvoiceVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Invoice interface
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
public interface IInvoiceService {

    /**
     * 新增数据
     *
     * @param invoiceDTO 保存参数
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceVO
     */
     InvoiceVO save(InvoiceDTO invoiceDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param invoiceDTO 保存参数
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceVO
     */
     InvoiceVO update(InvoiceDTO invoiceDTO);

    /**
     * 根据ID删除数据
     *
     * @param invoiceId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String invoiceId);
    /**
     * 根据ID获取详情
     *
     * @param invoiceId 对象ID
     * @return java.lang.Boolean
     */
    InvoiceVO detailById(String invoiceId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.order.api.vo.InvoiceVO> 返回列表数据
     */
    List<InvoiceVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.order.api.vo.InvoiceVO> 返回列表数据
     */
    Page<InvoiceVO> page(int page, int size);
}