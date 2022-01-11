package com.easy.cloud.web.component.mysql.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 重写IService接口类，新增before after操作
 *
 * @author GR
 * @date 2021-4-20 11:54
 */
public interface IRepositoryService<Entity> extends IService<Entity>,
        IOperationTreeService<Entity>,
        IOperationBeforeService<Entity>,
        IOperationAfterService<Entity> {

    @Override
    default boolean save(Entity entity) {
        Entity beforeSave = this.verifyBeforeSave(entity);
        if (Objects.isNull(beforeSave)) {
            return false;
        }

        boolean save = IService.super.save(beforeSave);
        this.verifyAfterSave(beforeSave);
        return save;
    }

    @Override
    default boolean saveBatch(Collection<Entity> entityList) {
        Collection<Entity> entities = this.verifyBeforeBatchSave(entityList);
        if (CollUtil.isEmpty(entities)) {
            return false;
        }

        boolean b = IService.super.saveBatch(entities);
        this.verifyAfterBatchSave(entities);
        return b;
    }

    @Override
    default boolean removeById(Serializable id) {
        this.verifyBeforeDelete(id);
        boolean b = IService.super.removeById(id);
        this.verifyAfterDelete(id);
        return b;
    }

    @Override
    default boolean removeByIds(Collection<? extends Serializable> idList) {
        this.verifyBeforeBatchDelete(idList);
        boolean b = IService.super.removeByIds(idList);
        this.verifyAfterBatchDelete(idList);
        return b;
    }

    @Override
    default boolean updateById(Entity entity) {
        Entity newEntity = this.verifyBeforeUpdate(entity);
        if (Objects.isNull(newEntity)) {
            return false;
        }

        boolean b = IService.super.updateById(newEntity);
        this.verifyAfterUpdate(newEntity);
        return b;
    }

    @Override
    default Entity getById(Serializable id) {
        Entity entity = IService.super.getById(id);
        this.verifyAfterDetail(entity);
        return entity;
    }

    @Override
    default List<Entity> listByIds(Collection<? extends Serializable> idList) {
        List<Entity> entities = IService.super.listByIds(idList);
        this.verifyAfterDetail(entities);
        return entities;
    }

    /**
     * 属性结构数据
     *
     * @param idList Id列表
     * @return java.util.List<Entity>
     */
    default List<Entity> treeByIds(Collection<? extends Serializable> idList) {
        return this.tree(IService.super.listByIds(idList));
    }

    @Override
    default List<Entity> listByMap(Map<String, Object> columnMap) {
        List<Entity> entities = IService.super.listByMap(columnMap);
        this.verifyAfterDetail(entities);
        return entities;
    }

    /**
     * 树形结构数据
     *
     * @param columnMap 查询条件
     * @return java.util.List<Entity>
     */
    default List<Entity> treeByMap(Map<String, Object> columnMap) {
        return this.tree(IService.super.listByMap(columnMap));
    }

    @Override
    default Entity getOne(Wrapper<Entity> queryWrapper) {
        Entity entity = IService.super.getOne(queryWrapper);
        this.verifyAfterDetail(entity);
        return entity;
    }

    @Override
    default List<Entity> list(Wrapper<Entity> queryWrapper) {
        List<Entity> list = IService.super.list(queryWrapper);
        this.verifyAfterDetail(list);
        return list;
    }

    /**
     * 树形结构数据
     *
     * @param queryWrapper 查询条件
     * @return java.util.List<Entity>
     */
    default List<Entity> tree(Wrapper<Entity> queryWrapper) {
        return this.tree(IService.super.list(queryWrapper));
    }

    @Override
    default List<Entity> list() {
        List<Entity> list = IService.super.list();
        this.verifyAfterDetail(list);
        return list;
    }

    /**
     * 属性列表信息
     *
     * @return java.util.List<Entity>
     */
    default List<Entity> tree() {
        return this.tree(IService.super.list());
    }

    @Override
    default <E extends IPage<Entity>> E page(E page, Wrapper<Entity> queryWrapper) {
        E page1 = IService.super.page(page, queryWrapper);
        this.verifyAfterDetail(page1);
        return page1;
    }

    @Override
    default <E extends IPage<Entity>> E page(E page) {
        E page1 = IService.super.page(page);
        this.verifyAfterDetail(page1);
        return page1;
    }
}
