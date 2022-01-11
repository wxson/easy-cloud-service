package com.easy.cloud.web.service.member.biz.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GR
 * @date 2021-11-26 12:34
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberStorageQuery {
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
