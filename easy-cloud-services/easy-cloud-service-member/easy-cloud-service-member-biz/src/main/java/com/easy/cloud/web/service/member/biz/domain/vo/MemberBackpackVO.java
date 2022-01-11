package com.easy.cloud.web.service.member.biz.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 会员背包
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberBackpackVO implements Serializable {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购买的商品编码
     */
    private String goodsNo;

    /**
     * 状态 0 启用 1 禁用
     */
    private Boolean status;

    /**
     * 创建用户
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private String createAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}