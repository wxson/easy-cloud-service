package com.easy.cloud.web.service.pay.biz.domain.dto;

import com.easy.cloud.web.service.pay.biz.enums.PayTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 支付内容
 *
 * @author GR
 * @date 2021-11-12 14:43
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "支付参数", description = "支付请求参数")
public class PayDTO {

    /**
     * O
     * 支付方式
     */
    @ApiModelProperty(name = "payType", value = "支付方式")
    private PayTypeEnum payType;

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "orderNo", value = "订单编号")
    private String orderNo;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "goodsNo", value = "用户ID")
    private String goodsNo;

    /**
     * 商品数量
     */
    @ApiModelProperty(name = "goodsNum", value = "商品数量")
    private Integer goodsNum;

}
