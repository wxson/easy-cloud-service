package com.easy.cloud.web.service.member.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * VIP 充值相关信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName(value = "db_vip_recharge")
public class VipRechargeDO implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Boolean deleted;

    /**
     * 创建用户
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private String createAt;

    /**
     * 更新用户
     */
    private String updaterAt;

    /**
     * 更新时间
     */
    private String updateAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}