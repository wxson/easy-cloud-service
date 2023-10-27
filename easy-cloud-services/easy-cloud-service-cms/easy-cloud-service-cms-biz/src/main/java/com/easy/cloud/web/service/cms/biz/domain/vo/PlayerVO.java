package com.easy.cloud.web.service.cms.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 玩家信息
 *
 * @author GR
 * @date 2021-11-29 19:50
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "玩家数据", description = "玩家返回数据")
public class PlayerVO implements IConvertProxy {
    /**
     * 房间信息
     */
    @ApiModelProperty(name = "updateAt", value = "更新时间")
    private String roomId;
    /**
     * 玩家ID
     */
    @ApiModelProperty(name = "playerId", value = "更新时间")
    private String playerId;
    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickName", value = "更新时间")
    private String nickName;
    /**
     * 玩家索引
     */
    @ApiModelProperty(name = "index", value = "更新时间")
    private Integer index;
    /**
     * 加减金额
     */
    @ApiModelProperty(name = "amount", value = "更新时间")
    private Long amount;
    /**
     * 0 加  1 减
     */
    @ApiModelProperty(name = "action", value = "更新时间")
    private Integer action;
}
