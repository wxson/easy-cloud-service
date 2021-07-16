package com.easy.cloud.web.component.mongo.page;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 默认分页
 *
 * @author GR
 * @date 2020-11-6 11:53
 */
@Data
@Accessors(chain = true)
public class SimplePage<Entity> implements IPage<Entity> {
    private int page;
    private long total;
    private int pageSize;
    private List<? extends Entity> records;

    public SimplePage() {
        this.page = 1;
        this.pageSize = 10;
    }
}
