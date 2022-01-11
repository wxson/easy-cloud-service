package com.easy.cloud.web.service.upms.biz.domain.bo;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.service.upms.biz.enums.PermissionActionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色权限操作类
 *
 * @author GR
 * @date 2020-11-13 13:25
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class PermissionActionBO {
    /**
     * code
     */
    private Integer code;
    /**
     * add/update/delete。。。
     */
    private String action;
    /**
     * icon
     */
    private String icon;
    /**
     * 新增、删除、更新。。。
     */
    private String describe;
    /**
     * 点击是否校验
     */
    private Boolean defaultCheck;

    /**
     * 获取默认的权限操作
     *
     * @param permissionActionEnums 用户自定义权限action
     */
    public static List<PermissionActionBO> defaultPermissionActions(PermissionActionEnum... permissionActionEnums) {
        List<PermissionActionBO> permissionActionBOList = new ArrayList<>();
        // 构建用户权限action map
        Map<Integer, PermissionActionEnum> permissionActionEnumMap = CollUtil.newArrayList(permissionActionEnums).stream().collect(Collectors.toMap(PermissionActionEnum::getCode, permissionActionEnum -> permissionActionEnum));
        for (PermissionActionEnum permissionActionEnum : PermissionActionEnum.values()) {
            Optional<PermissionActionEnum> actionEnumOptional = Optional.ofNullable(permissionActionEnumMap.get(permissionActionEnum.getCode()));
            // 当前action是否为用户自定义的
            if (actionEnumOptional.isPresent()) {
                permissionActionEnum = actionEnumOptional.get();
            }

            permissionActionBOList.add(PermissionActionBO.build()
                    .setCode(permissionActionEnum.getCode())
                    .setAction(permissionActionEnum.getFiled())
                    .setIcon(permissionActionEnum.getIcon())
                    .setDefaultCheck(permissionActionEnum.getDefaultCheck())
                    .setDescribe(permissionActionEnum.getDesc()));
        }
        return permissionActionBOList;
    }
}
