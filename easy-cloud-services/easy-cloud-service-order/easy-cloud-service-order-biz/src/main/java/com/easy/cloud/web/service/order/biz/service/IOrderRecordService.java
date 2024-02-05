package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.dto.OrderRecordQueryDTO;
import com.easy.cloud.web.service.order.api.vo.OrderRecordVO;

import java.util.List;

/**
 * OrderRecord interface
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
public interface IOrderRecordService {

    /**
     * 新增数据
     *
     * @param orderRecordDTO 保存参数
     * @return com.easy.cloud.web.service.order.api.vo.OrderRecordVO
     */
    OrderRecordVO save(OrderRecordDTO orderRecordDTO);

    /**
     * 根据ID获取详情
     *
     * @param orderRecordId 对象ID
     * @return java.lang.Boolean
     */
    OrderRecordVO detailById(String orderRecordId);

    /**
     * 根据条件获取列表数据
     *
     * @param orderRecordQueryDTO 订单查询参数
     * @return List<com.easy.cloud.web.service.order.api.vo.OrderRecordVO> 返回列表数据
     */
    List<OrderRecordVO> list(OrderRecordQueryDTO orderRecordQueryDTO);
}