package com.easy.cloud.web.service.cms.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户对局记录
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "玩家对局记录数据", description = "玩家对局记录返回数据")
public class UserPlayRecordVO implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;

    /**
     * 房间ID
     */
    @ApiModelProperty(name = "roomId", value = "房间ID")
    private String roomId;

    /**
     * 玩家ID
     */
    @ApiModelProperty(name = "playerId", value = "玩家ID")
    private String playerId;

    /**
     * 玩家昵称
     */
    @ApiModelProperty(name = "nickName", value = "玩家昵称")
    private String nickName;

    /**
     * 玩家位置索引
     */
    @ApiModelProperty(name = "playerIndex", value = "玩家位置索引")
    private Integer playerIndex;

    /**
     * 对局结束输赢金额
     */
    @ApiModelProperty(name = "amount", value = "对局结束输赢金额")
    private Long amount;

    /**
     * 0 加金币，1 减金币
     */
    @ApiModelProperty(name = "action", value = "0 加金币，1 减金币")
    private Integer action;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createAt", value = "创建时间")
    private String createAt;

    /**
     * 玩家列表
     */
    @ApiModelProperty(name = "playerList", value = "玩家对局记录信息")
    private List<UserPlayRecordVO> playerList;
}