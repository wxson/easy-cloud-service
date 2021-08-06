package com.easy.cloud.web.module.route.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.log.annotation.OperationRecord;
import com.easy.cloud.web.module.route.domain.RouteConf;
import com.easy.cloud.web.module.route.service.RouteConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 动态路由配置
 *
 * @author GR
 * @date 2021-8-4 19:39
 */
@Slf4j
@RestController
@RequestMapping("route")
@AllArgsConstructor
@Api(value = "route", tags = "网关路由管理模块")
public class DynamicRouteConfController {

    private final RouteConfService routeConfService;

    /**
     * 保存信息
     *
     * @param routeConf 保存的实体信息
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/save")
    @ApiOperation(value = "统一新增信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一新增信息接口", type = OperationRecord.Type.INFO, action = OperationRecord.Action.ADD)
    public HttpResult<Boolean> save(@Validated @RequestBody RouteConf routeConf) {
        boolean save = routeConfService.save(routeConf);
        routeConfService.sendRouteConfChangeNotice(routeConf);
        return HttpResult.ok(save);
    }

    /**
     * 更新信息
     *
     * @param routeConf 更新的实体信息
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/update")
    @ApiOperation(value = "统一修改信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一修改信息接口", type = OperationRecord.Type.INFO, action = OperationRecord.Action.UPDATE)
    public HttpResult<Boolean> update(@Validated @RequestBody RouteConf routeConf) {
        boolean update = routeConfService.updateById(routeConf);
        routeConfService.sendRouteConfChangeNotice(routeConf);
        return HttpResult.ok(update);
    }

    /**
     * 删除信息
     *
     * @param id 信息ID
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @GetMapping("base/remove/{id}")
    @ApiOperation(value = "统一根据ID删除信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一根据ID删除信息接口", type = OperationRecord.Type.DANGER, action = OperationRecord.Action.DELETE)
    public HttpResult<Boolean> deleteById(@PathVariable @NotBlank(message = "ID不能为空") String id) {
        boolean remove = routeConfService.removeById(id);
        routeConfService.sendRouteConfChangeNotice(null);
        return HttpResult.ok(remove);
    }

    /**
     * 获取对象详情
     *
     * @param id 信息ID
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @GetMapping("base/detail/{id}")
    @ApiOperation(value = "统一获取信息详情接口")
    public HttpResult<RouteConf> detailById(@PathVariable String id) {
        return HttpResult.ok(routeConfService.getById(id));
    }

    /**
     * 获取所有列表信息
     *
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/list")
    @ApiOperation(value = "统一获取信息列表接口")
    public HttpResult<List<RouteConf>> list() {
        return HttpResult.ok(routeConfService.list());
    }
}
