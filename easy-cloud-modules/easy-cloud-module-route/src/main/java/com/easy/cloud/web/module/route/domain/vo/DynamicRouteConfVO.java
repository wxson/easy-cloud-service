package com.easy.cloud.web.module.route.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GR
 * @date 2021-03-23
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class DynamicRouteConfVO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}