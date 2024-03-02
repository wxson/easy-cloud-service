package com.easy.cloud.web.module.banner.api.vo;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
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
 * Banner展示数据
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BannerDTO", description = "广告出参")
public class BannerVO {
    /**
     * 文档ID
     */
    @ApiModelProperty(value = "文档ID", required = false)
    private String id;
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
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    /**
     * 轮播图图片地址
     */
    @ApiModelProperty(value = "轮播图图片地址", required = true)
    private String image;
    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = false)
    private String link;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;
    /**
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(value = "状态 0 启用 1 禁用", required = false)
    private StatusEnum status;
    /**
     * 是否删除 0 未删除 1 已删除
     */
    @ApiModelProperty(value = "是否删除 0 未删除 1 已删除", required = false)
    private DeletedEnum deleted;
}