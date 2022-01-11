package com.easy.cloud.web.service.cms.biz.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 活动奖品
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "商品数据", description = "商品返回数据")
public class ActionPrizeVO implements IConvertProxy {
    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "goodsNo", value = "商品唯一编号")
    private String goodsNo;
    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goodsName", value = "商品名称")
    private String goodsName;

    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodsType", value = "商品类型")
    private Integer goodsType;

    /**
     * 商品数量
     */
    @ApiModelProperty(name = "goodsNum", value = "商品数量")
    private Integer goodsNum;

}