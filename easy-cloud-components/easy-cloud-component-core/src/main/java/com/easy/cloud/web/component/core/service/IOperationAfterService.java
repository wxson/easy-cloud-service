package com.easy.cloud.web.component.core.service;

import java.util.Collection;

/**
 * 操作之后校验
 *
 * @author GR
 * @date 2020-11-3 14:42
 */
public interface IOperationAfterService<Entity, ID> {
    /**
     * 保存之后校验
     *
     * @param entity 校验参数
     */
    default void afterSave(Entity entity) {
    }

    /**
     * 批量保存之后校验
     *
     * @param collection 校验参数
     */
    default void afterBatchSave(Collection<Entity> collection) {
    }

    /**
     * 更新之后校验
     *
     * @param entity 校验参数
     */
    default void afterUpdate(Entity entity) {
    }

    /**
     * 更新之后校验
     *
     * @param collection 校验参数
     */
    default void afterBatchUpdate(Collection<Entity> collection) {
    }

    /**
     * 删除之前校验
     *
     * @param id 删除ID
     */
    default void afterDelete(ID id) {
    }
}
