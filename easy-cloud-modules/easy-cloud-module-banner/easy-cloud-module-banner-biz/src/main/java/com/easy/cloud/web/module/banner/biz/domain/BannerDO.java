package com.easy.cloud.web.module.banner.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.module.banner.api.enums.BannerDeviceEnum;
import com.easy.cloud.web.module.banner.api.enums.BannerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Banner 持久类
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_banner")
public class BannerDO extends BaseEntity {
    /**
     * 轮播图类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL  DEFAULT 'IMAGE' COMMENT '轮播图类型'")
    private BannerTypeEnum type;

    /**
     * 轮播图终端
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) COMMENT '轮播图终端'")
    private BannerDeviceEnum device;
    /**
     * 标题
     */
    @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '标题'")
    private String title;
    /**
     * 轮播图图片地址
     */
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '轮播图图片地址'")
    private String image;
    /**
     * 链接
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '链接'")
    private String link;
    /**
     * 广告编号
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '广告编号'")
    private Integer slotNumber;
    /**
     * 排序
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '排序'")
    private Integer sort;
}