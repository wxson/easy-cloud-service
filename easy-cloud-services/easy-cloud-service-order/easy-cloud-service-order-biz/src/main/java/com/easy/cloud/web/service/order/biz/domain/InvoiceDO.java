package com.easy.cloud.web.service.order.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.order.api.enums.InvoiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Invoice 持久类
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_invoice")
public class InvoiceDO extends BaseEntity {
    /**
     * 发票类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'NONE' COMMENT '发票类型'")
    private InvoiceTypeEnum type;
    /**
     * 发票抬头
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '发票抬头'")
    private String header;
    /**
     * 发票税号
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '发票税号'")
    private String taxNo;
    /**
     * 银行账号
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '银行账号'")
    private String bankAccount;
    /**
     * 开户行
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '开户行'")
    private String depositBank;
    /**
     * 联系电话
     */
    @Column(columnDefinition = "VARCHAR(11) COMMENT '联系电话'")
    private String tel;
    /**
     * 省份
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '省份'")
    private String province;
    /**
     * 城市
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '城市'")
    private String city;
    /**
     * 地区
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '地区'")
    private String district;
    /**
     * 街道
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '街道'")
    private String street;
    /**
     * 地址
     */
    @Column(columnDefinition = "VARCHAR(125) COMMENT '地址'")
    private String address;
}