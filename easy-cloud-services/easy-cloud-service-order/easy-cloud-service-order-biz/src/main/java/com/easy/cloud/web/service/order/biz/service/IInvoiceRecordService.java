package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.dto.InvoiceRecordDTO;
import com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * InvoiceRecord interface
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
public interface IInvoiceRecordService {

    /**
     * 新增数据
     *
     * @param invoiceRecordDTO 保存参数
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
     InvoiceRecordVO save(InvoiceRecordDTO invoiceRecordDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param invoiceRecordDTO 保存参数
     * @return com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO
     */
     InvoiceRecordVO update(InvoiceRecordDTO invoiceRecordDTO);

    /**
     * 根据ID删除数据
     *
     * @param invoiceRecordId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String invoiceRecordId);
    /**
     * 根据ID获取详情
     *
     * @param invoiceRecordId 对象ID
     * @return java.lang.Boolean
     */
    InvoiceRecordVO detailById(String invoiceRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO> 返回列表数据
     */
    List<InvoiceRecordVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO> 返回列表数据
     */
    Page<InvoiceRecordVO> page(int page, int size);
}