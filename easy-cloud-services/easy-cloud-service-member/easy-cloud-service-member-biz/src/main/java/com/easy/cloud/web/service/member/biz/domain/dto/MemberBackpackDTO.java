package com.easy.cloud.web.service.member.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.db.MemberBackpackDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员背包
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberBackpackDTO implements IConverter<MemberBackpackDO> {
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
}