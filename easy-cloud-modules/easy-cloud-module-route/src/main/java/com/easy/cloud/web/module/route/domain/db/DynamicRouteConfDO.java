package com.easy.cloud.web.module.route.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.module.route.domain.vo.DynamicRouteConfVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author GR
 * @date 2021-03-23
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName("db_dynamic_route_conf")
public class DynamicRouteConfDO implements IConverter<DynamicRouteConfVO> {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 断言，匹配，格式为JSON 字符串数组
     * 如：Path=/goods/**；网关自动匹配当前请求path，若包含/goods，则会自动匹配到mall-goods服务，并uri为lb://mall-goods的服务
     */
    private String predicates;
    /**
     * 路由ID，可自定义，不写系统会自动按一定算法进行赋值
     */
    private String routeId;
    /**
     * 过滤器
     */
    private String filters;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 路由服务
     */
    private String uri;
    /**
     * 路由名称
     */
    private String routeName;
    /**
     * 状态
     */
    private StatusEnum status;
    /**
     * 是否删除
     */
    private DeletedEnum deleted;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}