package com.easy.cloud.web.service.upms.biz.domain.query;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色查询条件集合
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "角色查询条件集合")
@NoArgsConstructor(staticName = "build")
public class RoleQuery {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id", notes = "")
    private Integer id;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示数量", name = "size", notes = "")
    protected long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", name = "current", notes = "")
    protected long current = 1;
}