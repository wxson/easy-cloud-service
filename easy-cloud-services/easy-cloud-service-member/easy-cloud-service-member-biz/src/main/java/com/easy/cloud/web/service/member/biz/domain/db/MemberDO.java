package com.easy.cloud.web.service.member.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 会员资产信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName(value = "db_member")
public class MemberDO implements IConverter<MemberVO> {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 金币
     */
    private Long amount;

    /**
     * 钻石
     */
    private Long diamond;

    /**
     * 点券
     */
    private Long coupon;

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
     * 更新用户
     */
    private String updaterAt;

    /**
     * 更新时间
     */
    private String updateAt;


    /**
     * 昵称
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 账号
     */
    @TableField(exist = false)
    private String account;
}