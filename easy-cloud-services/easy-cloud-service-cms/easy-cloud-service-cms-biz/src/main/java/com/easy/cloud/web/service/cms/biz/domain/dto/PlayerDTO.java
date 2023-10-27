package com.easy.cloud.web.service.cms.biz.domain.dto;

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
@ApiModel(value = "玩家参数", description = "玩家请求参数")
public class PlayerDTO implements IConvertProxy {
    /**
     * 房间信息
     */
    @ApiModelProperty(name = "roomId", value = "房间信息")
    private String roomId;
    /**
     * 玩家ID
     */
    @ApiModelProperty(name = "playerId", value = "玩家ID")
    private String playerId;
    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickName", value = "昵称")
    private String nickName;
    /**
     * 玩家索引
     */
    @ApiModelProperty(name = "index", value = "玩家索引")
    private Integer index;
    /**
     * 最大翻数
     */
    @ApiModelProperty(name = "maxRate", value = "最大翻数")
    private Integer maxRate;
    /**
     * 加减金额
     */
    @ApiModelProperty(name = "amount", value = "金额")
    private Long amount;
    /**
     * 0 加  1 减
     */
    @ApiModelProperty(name = "action", value = "加减金额：0 加  1 减")
    private Integer action;
}
