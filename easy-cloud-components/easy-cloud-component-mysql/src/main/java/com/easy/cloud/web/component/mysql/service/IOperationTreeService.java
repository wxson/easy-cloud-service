package com.easy.cloud.web.component.mysql.service;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.mysql.utils.EntityPropertyUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 保存接口
 *
 * @author GR
 * @date 2020-11-3 14:42
 */
public interface IOperationTreeService<Entity> {

    /**
     * 属性结构数据
     * 注意：实体对象中必须包含children字段（数组）、parentId、id
     *
     * @param list 列表信息
     * @return java.util.List<Entity>
     */
    default List<Entity> tree(List<Entity> list) {
        return this.tree(list, GlobalConstants.DEFAULT_TREE_PARENT_ID);
    }

    /**
     * 属性结构数据
     * 注意：实体对象中必须包含children字段（数组）、parentId、id
     *
     * @param list     列表信息
     * @param parentId 父级ID
     * @return java.util.List<Entity>
     */
    default List<Entity> tree(List<Entity> list, Serializable parentId) {
        return recursionMenuTreeNode(new LinkedList<>(list), parentId);
    }

    /**
     * 递归遍历构造菜单树形结结构
     *
     * @param menuLinkedList      集合
     * @param defaultTreeParentId 默认顶级父类ID
     */
    default List<Entity> recursionMenuTreeNode(LinkedList<Entity> menuLinkedList, Serializable defaultTreeParentId) {
        // 构建子节点
        List<Entity> childrenList = CollUtil.newArrayList();
        // 构建新的容器装载，否则会缺失对象信息
        LinkedList<Entity> linkedList = new LinkedList<>(menuLinkedList);
        // 遍历所有的menu
        for (Entity entity : linkedList) {
            if (EntityPropertyUtils.hasProperties(entity, "parentId")) {
                // 查询所有的初始节点
                Object parentId = EntityPropertyUtils.getPropertiesValue(entity, "parentId");
                if (Objects.nonNull(parentId)
                        && Objects.nonNull(defaultTreeParentId)
                        && parentId.toString().equals(defaultTreeParentId.toString())) {
                    try {
                        Constructor<Entity> declaredConstructor = (Constructor<Entity>) entity.getClass().getDeclaredConstructor();
                        declaredConstructor.setAccessible(true);
                        Entity treeNode = declaredConstructor.newInstance();
                        BeanUtils.copyProperties(entity, treeNode);
                        childrenList.add(treeNode);
                        // 移除节点，减少后续的遍历次数
                        menuLinkedList.remove(entity);
                        Method setChildren = treeNode.getClass().getMethod("setChildren", Class.forName("java.util.List"));
                        Object id = EntityPropertyUtils.getPropertiesValue(entity, "id");
                        if (Objects.isNull(id)) {
                            id = EntityPropertyUtils.getTableIdPropertiesValue(entity);
                        }

                        if (Objects.nonNull(id)) {
                            setChildren.invoke(treeNode, this.recursionMenuTreeNode(menuLinkedList, id.toString()));
                        }
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
                        if (e instanceof NoSuchMethodException) {

                        } else {
                            throw new RuntimeException("IOperationTreeService.recursionMenuTreeNode() 数据转换异常：" + e.getMessage());
                        }
                    }
                }
            }
        }
        return childrenList;
    }
}
