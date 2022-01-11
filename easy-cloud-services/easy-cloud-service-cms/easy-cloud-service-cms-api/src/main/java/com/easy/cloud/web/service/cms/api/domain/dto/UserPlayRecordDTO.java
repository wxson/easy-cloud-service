package com.easy.cloud.web.service.cms.api.domain.dto;

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
public class UserPlayRecordDTO {
    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 对局玩家列表
     */
    private List<PlayerDTO> playerList;
}
