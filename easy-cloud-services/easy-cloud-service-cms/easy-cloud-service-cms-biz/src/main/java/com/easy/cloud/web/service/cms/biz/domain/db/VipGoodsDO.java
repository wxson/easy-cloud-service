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


/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_vip_goods")
public class VipGoodsDO implements IConvertProxy {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 商品编码
     */
    private String goodsNo;

    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;

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