package com.easy.cloud.web.service.order.biz.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 支付宝支付回调对象
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class AliPayCallBackDTO {
    /**
     * 用户ID
     */
    @NotBlank(message = "商品编号不能为空")
    private String goodsNo;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    private Integer goodsNum;
}