package com.easy.cloud.web.service.member.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberPropertyRecordVO;
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
@TableName(value = "db_member_property_record")
public class MemberPropertyRecordDO implements IConverter<MemberPropertyRecordVO> {
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
     * 资产来源
     */
    private Integer origin;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 充值金额
     */
    private BigDecimal recharge;

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