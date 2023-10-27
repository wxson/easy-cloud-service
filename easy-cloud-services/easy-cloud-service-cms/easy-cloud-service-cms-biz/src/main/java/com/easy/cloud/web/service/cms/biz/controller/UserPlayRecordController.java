package com.easy.cloud.web.service.cms.biz.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.domain.db.UserPlayRecordDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.PlayerDTO;
import com.easy.cloud.web.service.cms.biz.domain.dto.UserPlayRecordDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.UserPlayRecordVO;
import com.easy.cloud.web.service.cms.biz.service.IUserPlayRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 玩家对局记录
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("player")
@AllArgsConstructor
@Api(value = "用户对局记录", tags = "用户对局记录")
public class UserPlayRecordController {

    private final IUserPlayRecordService userPlayRecordService;

    /**
     * 插入对局记录
     *
     * @param userPlayRecordDTO 对局记录
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @Inner
    @PostMapping("insert/play/record")
    @ApiOperation(value = "插入玩家对局记录")
    HttpResult<Boolean> insertPlayRecord(@RequestBody UserPlayRecordDTO userPlayRecordDTO) {
        // 获取对局玩家列表
        List<PlayerDTO> playerList = userPlayRecordDTO.getPlayerList();
        List<UserPlayRecordDO> userPlayRecordDOList = playerList.stream().map(playerDTO -> playerDTO.convertTo(UserPlayRecordDO.class)
                .setPlayerIndex(playerDTO.getIndex()))
                .collect(Collectors.toList());
        userPlayRecordService.saveBatch(userPlayRecordDOList);
        return HttpResult.ok();
    }

    /**
     * 查询对局记录
     *
     * @param playerDTO 玩家信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @GetMapping("play/record/list")
    @ApiOperation(value = "获取玩家对局记录")
    HttpResult<List<UserPlayRecordVO>> listPlayRecord(@RequestBody PlayerDTO playerDTO) {
        List<UserPlayRecordDO> userPlayRecordDOList = userPlayRecordService.list(Wrappers.<UserPlayRecordDO>lambdaQuery().eq(UserPlayRecordDO::getPlayerId, SecurityUtils.getAuthenticationUser().getId()));
        if (CollUtil.isEmpty(userPlayRecordDOList)) {
            return HttpResult.ok(CollUtil.newArrayList());
        }

        // 或去相关的对局列表
        List<String> userPlayerRoomIdList = userPlayRecordDOList.stream().map(UserPlayRecordDO::getRoomId).collect(Collectors.toList());
        List<UserPlayRecordDO> playRecordDOList = userPlayRecordService.list(Wrappers.<UserPlayRecordDO>lambdaQuery()
                .in(CollUtil.isNotEmpty(userPlayerRoomIdList), UserPlayRecordDO::getRoomId, userPlayerRoomIdList));

        // 返回对局记录
        List<UserPlayRecordVO> recordVOList = playRecordDOList.stream()
                .collect(Collectors.groupingBy(UserPlayRecordDO::getRoomId))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<UserPlayRecordVO> userPlayRecordVOList = entry.getValue().stream().map(UserPlayRecordDO::convert).collect(Collectors.toList());
                    UserPlayRecordVO userPlayRecordVO = UserPlayRecordVO.build().setRoomId(entry.getKey()).setPlayerList(userPlayRecordVOList);
                    if (CollUtil.isNotEmpty(userPlayRecordVOList)) {
                        userPlayRecordVO.setCreateAt(userPlayRecordDOList.get(GlobalCommonConstants.ZERO).getCreateAt());
                    }
                    return userPlayRecordVO;
                }).collect(Collectors.toList());
        return HttpResult.ok(recordVOList);
    }
}
