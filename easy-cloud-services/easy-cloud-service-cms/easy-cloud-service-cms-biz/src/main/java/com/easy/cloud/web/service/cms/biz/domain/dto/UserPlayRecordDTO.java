package com.easy.cloud.web.service.cms.biz.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 玩家对局记录
 *
 * @author GR
 * @date 2021-11-29 19:50
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "玩家对局参数", description = "玩家对局请求参数")
public class UserPlayRecordDTO {
    /**
     * 房间ID
     */
    @ApiModelProperty(name = "roomId", value = "房间信息")
    private String roomId;

    /**
     * 对局玩家列表
     */
    @ApiModelProperty(name = "playerList", value = "玩家列表")
    private List<PlayerDTO> playerList;
}
