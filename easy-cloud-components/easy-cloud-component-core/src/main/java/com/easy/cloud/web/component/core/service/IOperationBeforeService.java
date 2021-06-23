package com.easy.cloud.web.component.core.service;

import java.util.Collection;

/**
 * 保存接口
 *
 * @author GR
 * @date 2020-11-3 14:42
 */
public interface IOperationBeforeService<Entity, ID> {
    /**
     * 保存之前校验
     *
     * @param entity 校验参数
     * @return Entity
     */
    default Entity beforeSave(Entity entity) {
        return entity;
    }

    /**
     * 批量保存之前校验
     *
     * @param collection 校验参数
     * @return java.util.Collection<Entity>
     */
    default Collection<Entity> beforeBatchSave(Collection<Entity> collection) {
        return collection;
    }

    /**
     * 更新之前校验
     *
     * @param entity 校验参数
     * @return Entity
     */
    default Entity beforeUpdate(Entity entity) {
        return entity;
    }

    /**
     * 更新之前校验
     *
     * @param collection 校验参数
     * @return Entity
     */
    default Collection<Entity> beforeBatchUpdate(Collection<Entity> collection) {
        return collection;
    }

    /**
     * 删除之前校验
     *
     * @param id 删除ID
     */
    default void beforeDelete(ID id) {
    }
}
