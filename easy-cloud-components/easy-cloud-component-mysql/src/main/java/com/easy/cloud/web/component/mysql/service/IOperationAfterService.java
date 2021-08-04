package com.easy.cloud.web.component.mysql.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 操作之后校验
 *
 * @author GR
 * @date 2020-11-3 14:42
 */
public interface IOperationAfterService<Entity> {
    /**
     * 保存之后校验
     *
     * @param entity 校验参数
     */
    default void verifyAfterSave(Entity entity) {
    }

    /**
     * 批量保存之后校验
     *
     * @param collection 校验参数
     */
    default void verifyAfterBatchSave(Collection<Entity> collection) {
    }

    /**
     * 更新之后校验
     *
     * @param entity 校验参数
     */
    default void verifyAfterUpdate(Entity entity) {
    }

    /**
     * 删除之前校验
     *
     * @param id 删除ID
     */
    default void verifyAfterDelete(Serializable id) {
    }

    /**
     * 批量删除之前校验
     *
     * @param idList 删除ID集合
     */
    default void verifyAfterBatchDelete(Collection<? extends Serializable> idList) {
    }

    /**
     * 获取详情之后检验或二次封装
     *
     * @param entity 实体详情
     */
    default void verifyAfterDetail(Entity entity) {
    }

    /**
     * 获取详情之后检验或二次封装
     *
     * @param list 实体详情
     */
    default void verifyAfterDetail(List<Entity> list) {
    }

    /**
     * 获取详情之后检验或二次封装
     *
     * @param page 实体详情
     */
    default void verifyAfterDetail(IPage<Entity> page) {
    }
}
