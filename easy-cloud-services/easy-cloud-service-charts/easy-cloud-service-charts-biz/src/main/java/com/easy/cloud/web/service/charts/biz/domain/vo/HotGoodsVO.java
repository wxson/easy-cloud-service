package com.easy.cloud.web.service.charts.biz.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 热销商品
 *
 * @author GR
 * @date 2021-12-26 16:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class HotGoodsVO {
    /**
     * 商品编码
     */
    private String goodsNo;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 购买次数
     */
    private Integer buyCount;
}
