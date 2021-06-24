package com.easy.cloud.web.component.mongo.page;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 分页
 *
 * @author GR
 * @date 2020-11-6 11:35
 */
public interface IPage<Entity> {

    /**
     * 设置总数
     *
     * @param total 总数
     * @return void
     */
    IPage<Entity> setTotal(long total);

    /**
     * 设置总数
     *
     * @param page 页数
     * @return void
     */
    IPage<Entity> setPage(int page);

    /**
     * 设置总数
     *
     * @param pageSize 每页数量
     * @return void
     */
    IPage<Entity> setPageSize(int pageSize);

    /**
     * 获取总数
     *
     * @return long
     */
    long getTotal();

    /**
     * 获取当前页
     *
     * @return int
     */
    int getPage();

    /**
     * 获取当前页大小
     *
     * @return int
     */
    int getPageSize();

    /**
     * 分页记录列表
     *
     * @return 分页对象记录列表
     */
    <T extends Entity> List<T> getRecords();

    /**
     * 设置分页记录列表
     *
     * @param records 设置的记录
     */
    IPage<Entity> setRecords(List<? extends Entity> records);

    /**
     * 分页数据转换
     *
     * @param mapper 转换规则
     */
    default <R> IPage<R> convert(Function<? super Entity, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        return ((IPage<R>) this).setRecords(collect);
    }

}
