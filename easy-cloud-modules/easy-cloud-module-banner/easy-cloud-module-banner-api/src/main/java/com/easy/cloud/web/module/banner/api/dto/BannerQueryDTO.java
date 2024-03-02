package com.easy.cloud.web.module.banner.api.dto;

import com.easy.cloud.web.module.banner.api.enums.BannerDeviceEnum;
import com.easy.cloud.web.module.banner.api.enums.BannerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Banner请求数据
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BannerDTO", description = "广告入参")
public class BannerQueryDTO {
    /**
     * 轮播图类型
     */
    @ApiModelProperty(value = "轮播图类型", required = false)
    private BannerTypeEnum type;
    /**
     * 轮播图终端
     */
    @ApiModelProperty(value = "轮播图终端", required = false)
    private BannerDeviceEnum device;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = false)
    private String title;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", required = false)
    private Integer page;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", required = false)
    private Integer size;
}