package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.vo.OrderRecordVO;
import com.easy.cloud.web.service.order.biz.domain.OrderRecordDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderRecord转换器
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
public class OrderRecordConverter {

    /**
     * DTO转为DO
     *
     * @param orderRecord 转换数据
     * @return com.easy.cloud.web.service.order.biz.domain.OrderRecordDO
     */
    public static OrderRecordDO convertTo(OrderRecordDTO orderRecord) {
        OrderRecordDO orderRecordDO = OrderRecordDO.builder().build();
        BeanUtils.copyProperties(orderRecord, orderRecordDO, true);
        return orderRecordDO;
    }

    /**
     * DO转为VO
     *
     * @param orderRecord 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.OrderRecordVO
     */
    public static OrderRecordVO convertTo(OrderRecordDO orderRecord) {
        OrderRecordVO orderRecordVO = OrderRecordVO.builder().build();
        BeanUtils.copyProperties(orderRecord, orderRecordVO, true);
        return orderRecordVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param orderRecords 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.OrderRecordVO
     */
    public static List<OrderRecordVO> convertTo(List<OrderRecordDO> orderRecords) {
        return orderRecords.stream()
                .map(OrderRecordConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.service.order.api.vo.OrderRecordVO
     */
    public static Page<OrderRecordVO> convertTo(Page<OrderRecordDO> page) {
        return page.map(OrderRecordConverter::convertTo);
    }
}