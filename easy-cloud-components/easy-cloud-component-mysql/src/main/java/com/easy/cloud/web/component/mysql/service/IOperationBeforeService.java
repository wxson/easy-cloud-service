package com.easy.cloud.web.component.mysql.service;

import java.io.Serializable;
import java.util.Collection;

/**
 * 保存接口
 *
 * @author GR
 * @date 2020-11-3 14:42
 */
public interface IOperationBeforeService<Entity> {
    /**
     * 保存之前校验
     *
     * @param entity 校验参数
     * @return Entity
     */
    default Entity verifyBeforeSave(Entity entity) {
        return entity;
    }

    /**
     * 批量保存之前校验
     *
     * @param collection 校验参数
     * @return java.util.Collection<Entity>
     */
    default Collection<Entity> verifyBeforeBatchSave(Collection<Entity> collection) {
        return collection;
    }

    /**
     * 更新之前校验
     *
     * @param entity 校验参数
     * @return Entity
     */
    default Entity verifyBeforeUpdate(Entity entity) {
        return entity;
    }

    /**
     * 删除之前校验
     *
     * @param id 删除ID
     */
    default void verifyBeforeDelete(Serializable id) {
    }

    /**
     * 批量删除之前校验
     *
     * @param idList 删除ID集合
     */
    default void verifyBeforeBatchDelete(Collection<? extends Serializable> idList) {
    }
}
