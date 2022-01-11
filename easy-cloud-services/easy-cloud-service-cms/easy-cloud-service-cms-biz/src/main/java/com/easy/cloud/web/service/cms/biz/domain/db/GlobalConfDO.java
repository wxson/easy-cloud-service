package com.easy.cloud.web.service.cms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统全局配置表
 * 注意：当前表内部信息的KEY不能随意变动，一变动，代码层面将出现异常
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName(value = "db_global_conf")
public class GlobalConfDO implements Serializable {
    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * key
     */
    private String confKey;

    /**
     * value
     */
    private String confValue;

    /**
     * 配置描述
     */
    private String confDesc;

    /**
     * 状态 0 启用 1 禁用
     */
    private Byte status;

    /**
     * 创建人
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private String createAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}