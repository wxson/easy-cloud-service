package com.easy.cloud.web.service.cms.api.domain.dto;

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
public class PlayerDTO {
    /**
     * 房间信息
     */
    private String roomId;
    /**
     * 玩家ID
     */
    private String playerId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 玩家索引
     */
    private Integer index;
    /**
     * 最大翻数
     */
    private Integer maxRate;
    /**
     * 加减金额
     */
    private Long amount;
    /**
     * 0 加  1 减
     */
    private Integer action;
}
