package com.easy.cloud.web.service.cms.biz.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "商城数据", description = "商城返回数据")
public class ShopVO implements IConvertProxy {
    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodsType", value = "商品类型")
    private Integer goodsType;
    /**
     * 商品类型名称
     */
    @ApiModelProperty(name = "goodsTypeName", value = "商品类型名称")
    private String goodsTypeName;
    /**
     * 商品列表
     */
    @ApiModelProperty(name = "goodsList", value = "商品列表")
    private List<GoodsVO> goodsList;

}