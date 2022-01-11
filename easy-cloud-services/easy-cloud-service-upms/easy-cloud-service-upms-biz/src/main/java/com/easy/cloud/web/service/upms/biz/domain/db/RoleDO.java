package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.vo.RoleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author Fast Java
 * @date 2021-04-01
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_role")
public class RoleDO implements IConverter<RoleVO> {
    /**
     * 文档ID，必须保证角色ID的全局唯一性
     */
    private String id;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;
    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer deleted;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新人员
     */
    private String updaterAt;
    /**
     * 更新时间
     */
    private String updateAt;
}