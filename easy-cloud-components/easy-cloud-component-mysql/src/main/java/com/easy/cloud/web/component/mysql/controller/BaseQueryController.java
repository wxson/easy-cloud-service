package com.easy.cloud.web.component.mysql.controller;

import cn.hutool.core.lang.ParameterizedTypeImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.utils.EntityPropertyUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

//import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

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
public abstract class BaseQueryController<QueryCondition, Entity> implements IController<Entity> {

    /**
     * 新增默认排序方法
     *
     * @param queryWrapper 排序方法
     */
    protected void defaultOrder(QueryWrapper<Entity> queryWrapper) {

    }

    /**
     * 获取父级泛型
     * 注意：必须为ServiceImpl的子类
     *
     * @return java.lang.Class<Entity>
     */
    protected Class<Entity> getSupperClass() {
        return this.recursionGenericSuperclass(this.getService().getClass().getSuperclass().getGenericSuperclass(), ServiceImpl.class, 2);
    }

    /**
     * 递归获取当前类
     *
     * @param genericSuperclass 超类
     * @param currentClass      当前类
     * @param index             获取泛型的位置，起始值 1
     * @return java.lang.Class<Entity>
     */
    private Class<Entity> recursionGenericSuperclass(Type genericSuperclass, Class<?> currentClass, Integer index) {
        ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) genericSuperclass;
        if (parameterizedType.getRawType().getTypeName().equals(currentClass.getSimpleName())) {
            if (parameterizedType.getActualTypeArguments().length > index - 1) {
                return (Class<Entity>) parameterizedType.getActualTypeArguments()[index - 1];
            }
        }

        return recursionGenericSuperclass(parameterizedType.getRawType().getClass().getSuperclass(), currentClass, index);
    }

    /**
     * 根据ID获取详情
     *
     * @param id ID
     * @return Entity
     */
    public Entity findById(String id) {
        return this.getService().getById(id);
    }

    /**
     * 获取对象详情
     *
     * @param id 信息ID
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/detail/{id}")
    @ApiOperation(value = "统一获取信息详情接口")
    public HttpResult<Entity> detailById(@PathVariable String id) {
        return HttpResult.ok(this.findById(id));
    }

    /**
     * 构建Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof List) {
                // 若为集合
                if (((List) value).size() > 0) {
                    query.in(key, ((List) value).toArray());
                }
            } else {
                query.eq(key, value);
            }
        });
        this.defaultOrder(query);
        return query;
    }

    /**
     * 构建非Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildNoWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof List) {
                // 若为集合
                if (((List) value).size() > 0) {
                    query.notIn(key, ((List) value).toArray());
                }
            } else {
                query.ne(key, value);
            }
        });
        this.defaultOrder(query);
        return query;
    }

    /**
     * 构建Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildLikeWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof List) {
                // 若为集合
                if (((List) value).size() > 0) {
                    query.notIn(key, ((List) value).toArray());
                }
            } else if (value instanceof String) {
                query.like(key, value);
            } else {
                query.eq(key, value);
            }
        });
        this.defaultOrder(query);
        return query;
    }

    /**
     * 构建Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildNoLikeWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof List) {
                // 若为集合
                if (((List) value).size() > 0) {
                    query.notIn(key, ((List) value).toArray());
                }
            } else if (value instanceof String) {
                query.notLike(key, value);
            } else {
                query.ne(key, value);
            }
        });
        this.defaultOrder(query);
        return query;
    }

    /**
     * 根据条件查询列表
     *
     * @param queryCondition 查询条件
     * @return java.util.List<Entity>
     */
    public List<Entity> findList(QueryCondition queryCondition) {
        return this.getService().list(this.buildWrapper(queryCondition));
    }

    /**
     * 获取所有列表信息
     *
     * @param queryCondition 实体对象
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/list")
    @ApiOperation(value = "统一获取信息列表接口")
    public HttpResult<List<Entity>> list(@RequestBody QueryCondition queryCondition) {
        return HttpResult.ok(this.findList(queryCondition));
    }

    /**
     * 根据条件查询列表
     *
     * @param queryCondition 查询条件
     * @return java.util.List<Entity>
     */
    public List<Entity> findNoList(QueryCondition queryCondition) {
        return this.getService().list(this.buildNoWrapper(queryCondition));
    }

    /**
     * 获取所有列表信息(不等于)
     *
     * @param queryCondition 实体对象
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/no/list")
    @ApiOperation(value = "统一获取信息列表接口（不等于）")
    public HttpResult<List<Entity>> noList(@RequestBody QueryCondition queryCondition) {
        return HttpResult.ok(this.findNoList(queryCondition));
    }


    /**
     * 根据条件模糊查询列表
     *
     * @param queryCondition 查询条件
     * @return java.util.List<Entity>
     */
    public List<Entity> findLikeList(QueryCondition queryCondition) {
        return this.getService().list(this.buildLikeWrapper(queryCondition));
    }

    /**
     * 模糊查询所有列表信息
     *
     * @param queryCondition 实体对象
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/like/list")
    @ApiOperation(value = "统一模糊获取信息列表接口")
    public HttpResult<List<Entity>> likeList(@RequestBody QueryCondition queryCondition) {
        return HttpResult.ok(this.findLikeList(queryCondition));
    }

    /**
     * 初始化分页信息
     * 主要是为了方便前端有时候会将分页信息写入body中
     *
     * @param queryCondition 实体信息
     * @param iPage          分页信息
     */
    protected void initPage(QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        // 如果存在页面
        if (EntityPropertyUtils.hasProperties(queryCondition, CURRENT)) {
            Optional.ofNullable(EntityPropertyUtils.getPropertiesValue(queryCondition, CURRENT)).ifPresent(page -> iPage.setCurrent((Long) page));
        }

        // 如果存在每页大小
        if (EntityPropertyUtils.hasProperties(queryCondition, SIZE)) {
            Optional.ofNullable(EntityPropertyUtils.getPropertiesValue(queryCondition, SIZE)).ifPresent(pageSize -> iPage.setSize((Long) pageSize));
        }
    }

    /**
     * 根据条件分页查询
     *
     * @param queryCondition 查询条件
     * @param iPage          分页信息
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<Entity>
     */
    public IPage<Entity> findPage(QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        return this.getService().page(iPage, this.buildWrapper(queryCondition));
    }

    /**
     * 获取分页信息
     *
     * @param queryCondition 实体信息
     * @param iPage          分页信息
     * @return reactor.core.publisher.Mono<com.baomidou.mybatisplus.core.metadata.IPage < Entity>>
     */
    @PostMapping("base/page")
    @ApiOperation(value = "统一获取分页信息接口")
    public HttpResult<IPage<Entity>> page(@RequestBody QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        this.initPage(queryCondition, iPage);
        return HttpResult.ok(this.findPage(queryCondition, iPage));
    }

    /**
     * 根据条件分页查询(不等于)
     *
     * @param queryCondition 查询条件
     * @param iPage          分页信息
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<Entity>
     */
    public IPage<Entity> findNoPage(QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        return this.getService().page(iPage, this.buildNoWrapper(queryCondition));
    }

    /**
     * 根据条件分页查询(不等于)
     *
     * @param queryCondition 查询条件
     * @param iPage          分页信息
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<Entity>
     */
    public IPage<Entity> findNoLikePage(QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        return this.getService().page(iPage, this.buildNoLikeWrapper(queryCondition));
    }

    /**
     * 获取分页信息
     *
     * @param queryCondition 实体信息
     * @param iPage          分页信息
     * @return reactor.core.publisher.Mono<com.baomidou.mybatisplus.core.metadata.IPage < Entity>>
     */
    @PostMapping("base/no/page")
    @ApiOperation(value = "统一获取分页信息接口（不等于）")
    public HttpResult<IPage<Entity>> noPage(@RequestBody QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        this.initPage(queryCondition, iPage);
        return HttpResult.ok(this.findNoPage(queryCondition, iPage));
    }

    /**
     * 获取分页信息
     *
     * @param queryCondition 实体信息
     * @param iPage          分页信息
     * @return reactor.core.publisher.Mono<com.baomidou.mybatisplus.core.metadata.IPage < Entity>>
     */
    @PostMapping("base/no/like/page")
    @ApiOperation(value = "统一模糊获取分页信息接口（不等于）")
    public HttpResult<IPage<Entity>> noLikePage(@RequestBody QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        this.initPage(queryCondition, iPage);
        return HttpResult.ok(this.findNoLikePage(queryCondition, iPage));
    }


    /**
     * 根据条件模糊分页查询
     *
     * @param queryCondition 查询条件
     * @param iPage          分页信息
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<Entity>
     */
    public IPage<Entity> findLikePage(QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        return this.getService().page(iPage, this.buildLikeWrapper(queryCondition));
    }

    /**
     * 模糊获取分页信息
     *
     * @param queryCondition 实体信息
     * @param iPage          分页信息
     * @return reactor.core.publisher.Mono<com.baomidou.mybatisplus.core.metadata.IPage < Entity>>
     */
    @PostMapping("base/like/page")
    @ApiOperation(value = "统一模糊获取分页信息接口")
    public HttpResult<IPage<Entity>> likePage(@RequestBody QueryCondition queryCondition, @ApiParam(hidden = true) Page<Entity> iPage) {
        this.initPage(queryCondition, iPage);
        return HttpResult.ok(this.findLikePage(queryCondition, iPage));
    }
}
