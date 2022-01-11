package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import com.easy.cloud.web.service.upms.biz.domain.db.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.db.PermissionDO;
import com.easy.cloud.web.service.upms.biz.domain.vo.MenuVO;
import com.easy.cloud.web.service.upms.biz.enums.PermissionTypeEnum;
import com.easy.cloud.web.service.upms.biz.mapper.MenuMapper;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单业务逻辑层
 *
 * @author GR
 * @date 2020-11-4 15:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements IMenuService {

    @Override
    public MenuDO verifyBeforeSave(MenuDO menuDO) {
        // 统一层级下禁止出现同名菜单
        MenuDO existMenuDO = this.getOne(Wrappers.<MenuDO>lambdaQuery()
                .eq(MenuDO::getParentId, menuDO.getParentId())
                .eq(MenuDO::getName, menuDO.getName()));
        if (null != existMenuDO) {
            throw new BusinessException("当前已存在同名的菜单，请重新输入");
        }

        return menuDO;
    }

    /**
     * 保存之后进行存储权限信息
     *
     * @param menuDO 保存信息
     */
    @Override
    public void verifyAfterSave(MenuDO menuDO) {
        // 保存关联的权限信息
        this.savePermission(menuDO.getId(), PermissionTypeEnum.MENU);
    }

    /**
     * 删除菜单之前移除相关的权限信息
     *
     * @param menuId 要删除的权限
     */
    @Override
    public void verifyBeforeDelete(Serializable menuId) {
        MenuDO menuDO = this.getById(menuId);
        if (null == menuDO) {
            throw new BusinessException("当前信息不存在");
        }

        // 移除关联的权限信息
        this.removeRelationPermission(menuId);
    }

    /**
     * 递归遍历构造菜单树形结结构
     *
     * @param menuLinkedList      集合
     * @param defaultTreeParentId 默认顶级父类ID
     */
    private List<MenuVO> recursionMenuTreeNode(LinkedList<MenuVO> menuLinkedList, String defaultTreeParentId) {
        // 构建子节点
        List<MenuVO> childrenList = CollUtil.newArrayList();
        // 排序
        menuLinkedList.sort(Comparator.comparing(MenuVO::getSort));
        // 构建新的容器装载，否则会缺失对象信息
        LinkedList<MenuVO> linkedList = new LinkedList<>(menuLinkedList);
        // 遍历所有的menu
        linkedList.forEach(menuVO -> {
            // 查询所有的初始节点
            if (defaultTreeParentId.equals(menuVO.getParentId())) {
                MenuVO treeNode = MenuVO.build();
                BeanUtils.copyProperties(menuVO, treeNode);
                childrenList.add(treeNode);
                // 移除节点，减少后续的遍历次数
                menuLinkedList.remove(menuVO);
                treeNode.setChildren(this.recursionMenuTreeNode(menuLinkedList, menuVO.getId()));
            }
        });
        return childrenList;
    }

    @Override
    public List<MenuVO> findAllTreeMenus() {
        List<MenuVO> menuVOList = this.list().stream()
                .map(menuDO -> menuDO.convert()).collect(Collectors.toList());
        return this.recursionMenuTreeNode(new LinkedList<>(menuVOList), GlobalConstants.DEFAULT_TREE_PARENT_ID);
    }

    @Override
    public List<MenuVO> findMenusByUserId(String userId) {
        String channelId = SecurityUtils.getAuthenticationUser().getChannelId();
        return this.findMenusByRoleId(channelId);
    }

    @Override
    public List<MenuVO> findMenusByRoleId(String roleId) {
        // 获取当前角色下的所有菜单信息
        List<PermissionDO> permissionDOList = this.findPermissionByRoleIdAndType(roleId, PermissionTypeEnum.MENU);
        // 获取所有菜单ID
        List<String> menuIdList = permissionDOList.stream().map(PermissionDO::getTargetId).collect(Collectors.toList());
        // 获取菜单权限Map对象
        Map<String, List<PermissionActionBO>> permissionMap = permissionDOList.stream().collect(Collectors.toMap(PermissionDO::getTargetId, PermissionDO::getActions));
        // 获取所有具体的菜单对象集合
        List<MenuVO> menuVOList = this.list(Wrappers.<MenuDO>lambdaQuery()
                .eq(MenuDO::getStatus, StatusEnum.START_STATUS)
                .eq(MenuDO::getDeleted, DeletedEnum.UN_DELETED))
                .stream()
                .map(menuDO -> menuDO.convert()).collect(Collectors.toList());
        // 查询所有符合条件的菜单列表
        return this.recursionMenuTreeNode(new LinkedList<>(menuVOList), GlobalConstants.DEFAULT_TREE_PARENT_ID);
    }

    @Override
    public MenuVO moveMenuPosition(String parentId, String menuId) {
        if (StrUtil.isBlank(menuId)) {
            throw new BusinessException("当前菜单ID不能为空");
        }

        MenuDO menuDO = this.getById(menuId);
        if (null == menuDO) {
            throw new BusinessException("当前菜单ID不存在");
        }

        // 若当前移动位置不变，则不更新
        if (menuDO.getParentId().equals(parentId)) {
            return menuDO.convert();
        }

        // 设置新的父级菜单
        menuDO.setParentId(parentId);
        // 执行更新
        this.update(Wrappers.<MenuDO>lambdaUpdate()
                .eq(MenuDO::getId, menuId)
                .set(StrUtil.isNotBlank(parentId), MenuDO::getParentId, parentId));
        return menuDO.convert();
    }

    @Override
    public void initDefaultConfiguration() {
//        MongoIterable<String> mongoIterable = this.getMongoTemplate().getDb().listCollectionNames();
//        ArrayList<String> mongoTableNameList = CollUtil.newArrayList(mongoIterable);
//        Document annotation = MenuDO.class.getAnnotation(Document.class);
//        if (Objects.nonNull(annotation) && !mongoTableNameList.contains(annotation.value())) {
//            log.info("-------------初始化系统默认菜单信息表--------------");
//            // 设置默认Security User
//            SecurityUtils.initSystemDefaultUser();
//            // 初始化四通管理菜单
//            MenuDO sysManagerMenuDO = MenuDO.builder()
//                    .title("系统设置")
//                    .name("system-manage")
//                    .icon("from")
//                    .parentId(CommonConstants.DEFAULT_TREE_PARENT_ID)
//                    .show(true)
//                    .sort(0)
//                    .key("SystemManage")
//                    .deleted(DeletedEnum.UN_DELETED)
//                    .status(StatusEnum.START_STATUS)
//                    .build();
//            MenuDO saveSysManagerMenuDO = this.save(sysManagerMenuDO);
//            // 初始化菜单管理
//            MenuDO menuManagerMenuDO = MenuDO.builder()
//                    .title("菜单管理")
//                    .name("menu-manage")
//                    .icon("from")
//                    .parentId(saveSysManagerMenuDO.getId())
//                    .show(true)
//                    .sort(0)
//                    .key("MenuManage")
//                    .deleted(DeletedEnum.UN_DELETED)
//                    .status(StatusEnum.START_STATUS)
//                    .build();
//            this.save(menuManagerMenuDO);
    }
}
