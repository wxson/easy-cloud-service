package com.easy.cloud.web.component.mysql.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.module.log.annotation.OperationRecord;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Controller基类
 * 说明：封装BaseController类的初衷只是为了去除重复的简单的业务常见逻辑的耦合性，并不是为实现一本万利，万能的控制类，
 * 该方式只适用于普通的常见的CRUD等常见的业务逻辑，且更适用于单表业务，当需要执行连表查询时，需维护BaseMapper的三个方法
 * <p>
 * QueryCondition：查询条件对象。一般为前端查询条件的集合
 * BaseForm：表单对象。一般为表对象的副本，前端上传的参数
 * Entity：表对象。一般对应一个表结构
 *
 * @author GR
 * @date 2021-4-20 9:16
 */
public abstract class BaseController<QueryCondition, Entity extends IConverter<?>> extends BaseQueryController<QueryCondition, Entity> {

    /**
     * 保存信息
     *
     * @param entity 保存的实体信息
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/save")
    @ApiOperation(value = "统一新增信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一新增信息接口", type = OperationRecord.Type.INFO, action = OperationRecord.Action.ADD)
    public HttpResult<Boolean> save(@Validated @RequestBody Entity entity) {
        boolean save = this.getService().save(entity);
        return HttpResult.ok(save);
    }

    /**
     * 保存信息
     *
     * @param list 保存数据
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */

    @PostMapping("base/batch/save")
    @ApiOperation(value = "统一批量新增信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一批量新增信息接口", type = OperationRecord.Type.INFO, action = OperationRecord.Action.ADD)
    public HttpResult<Boolean> batchSave(@Validated @RequestBody List<Entity> list) {
        boolean saveBatch = this.getService().saveBatch(list);
        return HttpResult.ok(saveBatch);
    }

    /**
     * 更新信息
     *
     * @param entity 更新的实体信息
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/update")
    @ApiOperation(value = "统一修改信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一修改信息接口", type = OperationRecord.Type.INFO, action = OperationRecord.Action.UPDATE)
    public HttpResult<Boolean> update(@Validated @RequestBody Entity entity) {
        boolean update = this.getService().updateById(entity);
        return HttpResult.ok(update);
    }

    /**
     * 删除信息
     *
     * @param id 信息ID
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/remove/{id}")
    @ApiOperation(value = "统一根据ID删除信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一根据ID删除信息接口", type = OperationRecord.Type.DANGER, action = OperationRecord.Action.DELETE)
    public HttpResult<Boolean> deleteById(@PathVariable @NotBlank(message = "ID不能为空") String id) {
        boolean remove = this.getService().removeById(id);
        return HttpResult.ok(remove);
    }

    /**
     * 条件删除信息
     *
     * @param queryCondition 条件
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/query/remove")
    @ApiOperation(value = "统一根据根据条件删除信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一根据根据条件删除信息接口", type = OperationRecord.Type.DANGER, action = OperationRecord.Action.DELETE)
    public HttpResult<Boolean> deleteByQuery(QueryCondition queryCondition) {
        boolean remove = this.getService().remove(this.buildWrapper(queryCondition));
        return HttpResult.ok(remove);
    }

    /**
     * 批量删除信息
     *
     * @param list ID列表
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/batch/remove")
    @ApiOperation(value = "统一批量根据ID列表删除信息接口")
    @Transactional(rollbackFor = Exception.class)
    @OperationRecord(value = "执行统一批量根据ID列表删除信息接口", type = OperationRecord.Type.DANGER, action = OperationRecord.Action.DELETE)
    public HttpResult<Boolean> batchDeleteByIds(@RequestBody List<String> list) {
        boolean remove = this.getService().removeByIds(list);
        return HttpResult.ok(remove);
    }
}
