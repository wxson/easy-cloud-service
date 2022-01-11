package com.easy.cloud.web.module.online.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author GR
 * @date 2021-12-21 9:43
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class OnlineDTO implements Serializable {
    /**
     * 新增用户ID
     */
    private String userId;
    /**
     * 新增用户昵称
     */
    private String nickName;
    /**
     * 新增用户头像
     */
    private String userAvatar;
}
