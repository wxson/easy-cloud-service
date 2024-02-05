package com.easy.cloud.web.service.order.api.vo;

import com.easy.cloud.web.service.order.api.enums.InvoiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Invoice展示数据
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceVO {

    /**
     * 发票类型
     */
    private InvoiceTypeEnum type;
    /**
     * 发票抬头
     */
    private String header;
    /**
     * 发票税号
     */
    private String taxNo;
    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 开户行
     */
    private String depositBank;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 地区
     */
    private String district;
    /**
     * 街道
     */
    private String street;
    /**
     * 地址
     */
    private String address;
}