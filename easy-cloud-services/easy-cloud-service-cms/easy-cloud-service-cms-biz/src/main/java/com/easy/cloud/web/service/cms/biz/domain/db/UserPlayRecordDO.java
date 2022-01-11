package com.easy.cloud.web.service.cms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.cms.biz.domain.vo.UserPlayRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户对局记录
 *
 * @author GR
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "db_user_play_record")
public class UserPlayRecordDO implements IConverter<UserPlayRecordVO> {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 玩家ID
     */
    private String playerId;

    /**
     * 玩家昵称
     */
    private String nickName;

    /**
     * 玩家位置索引
     */
    private Integer playerIndex;

    /**
     * 当前对局中最大翻数
     */
    private Integer maxRate;

    /**
     * 对局结束输赢金额
     */
    private Long amount;

    /**
     * 0 加金币，1 减金币
     */
    private Integer action;

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