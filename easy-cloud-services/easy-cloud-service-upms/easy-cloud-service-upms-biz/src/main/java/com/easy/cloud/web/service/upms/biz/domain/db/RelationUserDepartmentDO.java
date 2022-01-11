package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户部门关系表
 * 一般用于支付相关权限功能
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_relation_user_department")
public class RelationUserDepartmentDO implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 部门ID
     */
    private String departmentId;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}
