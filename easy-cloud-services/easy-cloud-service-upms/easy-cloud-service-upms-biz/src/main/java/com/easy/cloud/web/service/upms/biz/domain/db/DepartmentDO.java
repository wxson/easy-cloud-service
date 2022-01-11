package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.annotation.DefaultField;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.vo.DepartmentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author Fast Java
 * @date 2021-04-06
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_department")
public class DepartmentDO implements IConverter<DepartmentVO> {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上级部门
     */
    @DefaultField(strValue = GlobalConstants.DEFAULT_TREE_PARENT_ID)
    private String parentId;
    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;
    /**
     * 逻辑删除标记 0：未删除     1：已删除
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