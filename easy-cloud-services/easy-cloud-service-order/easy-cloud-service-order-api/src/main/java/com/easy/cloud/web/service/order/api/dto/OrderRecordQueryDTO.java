package com.easy.cloud.web.service.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OrderRecord请求数据
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecordQueryDTO {

    /**
     * 订单编号
     */
    private String orderNo;
}