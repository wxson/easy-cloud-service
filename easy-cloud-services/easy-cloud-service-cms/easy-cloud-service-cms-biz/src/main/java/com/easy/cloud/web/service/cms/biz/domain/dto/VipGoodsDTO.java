package com.easy.cloud.web.service.cms.biz.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.cms.biz.domain.db.VipGoodsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Vip商品参数", description = "Vip商品请求参数")
public class VipGoodsDTO implements IConverter<VipGoodsDO> {
    /**
     * 商品ID
     */
    @TableId
    @ApiModelProperty(name = "id", value = "商品ID")
    private Integer id;

    /**
     * Vip等级
     */
    @ApiModelProperty(required = true, name = "vipLevel", value = "Vip等级")
    private Integer vipLevel;

    /**
     * 对应的商品
     */
    @ApiModelProperty(required = true, name = "goodsNo", value = "对应的商品")
    private String goodsNo;

    /**
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(name = "status", value = "状态 0 启用 1 禁用")
    private Integer status;
}