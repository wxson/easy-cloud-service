package com.easy.cloud.web.service.order.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.order.api.enums.TradeInvoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * InvoiceRecord 持久类
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_invoice_record")
public class InvoiceRecordDO extends BaseEntity {
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 交易发票
     */
    private TradeInvoiceEnum type;
    /**
     * 发票抬头
     */
    private String header;
    /**
     * 税号
     */
    private String taxNo;
    /**
     * 发票金额
     */
    private Integer amount;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 发票接收地址
     */
    private String address;
}