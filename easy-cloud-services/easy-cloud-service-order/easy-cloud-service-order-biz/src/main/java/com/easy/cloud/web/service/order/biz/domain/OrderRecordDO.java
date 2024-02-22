package com.easy.cloud.web.service.order.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * OrderRecord 持久类
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_order_record")
public class OrderRecordDO extends BaseEntity {

    /**
     * 订单编号
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '订单编号'")
    private String orderNo;
    /**
     * 订单状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'NEW' COMMENT '订单状态'")
    private OrderStatusEnum orderStatus;
    /**
     * 订单备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '订单备注'")
    private String remark;
}