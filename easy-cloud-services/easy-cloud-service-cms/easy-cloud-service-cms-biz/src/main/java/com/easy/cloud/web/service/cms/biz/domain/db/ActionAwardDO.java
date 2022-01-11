package com.easy.cloud.web.service.cms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 活动奖励
 *
 * @author GR
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_action_award")
public class ActionAwardDO implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品唯一编码
     */
    private String goodsNo;

    /**
     * 是否已领取：0：已领取  1：未领取
     */
    private Integer receive;

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