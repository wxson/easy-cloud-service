package com.easy.cloud.web.service.cms.biz.domain.dto;

import com.easy.cloud.web.service.cms.biz.enums.ActionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "活动参数", description = "活动请求参数")
public class ActionDTO {

    /**
     * action
     */
    @NotNull(message = "action不能为空或传入的action错误")
    @ApiModelProperty(required = true, name = "action", value = "活动类型：首冲、月卡、签到等")
    private ActionEnum action;

    /**
     * 订单唯一编号
     */
    @ApiModelProperty(name = "orderNo", value = "订单唯一编号")
    private String orderNo;

    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "goodsNo", value = "活动对应的商品编码")
    private String goodsNo;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;

    /**
     * 身份证
     */
    @ApiModelProperty(name = "identity", value = "身份证")
    private String identity;

    /**
     * 手机号
     */
    @ApiModelProperty(required = true, name = "tel", value = "手机号")
    private String tel;
}