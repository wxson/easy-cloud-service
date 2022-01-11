package com.easy.cloud.web.service.cms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.cms.biz.domain.vo.GoodsVO;
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
@TableName("db_user_friends")
public class UserFriendsDO implements IConverter<GoodsVO> {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 被邀请人账号ID
     */
    private String inviteId;

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