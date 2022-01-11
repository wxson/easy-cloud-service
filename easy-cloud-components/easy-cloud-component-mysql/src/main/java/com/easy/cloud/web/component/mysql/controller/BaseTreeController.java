package com.easy.cloud.web.component.mysql.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.component.mysql.utils.EntityPropertyUtils;
import com.easy.cloud.web.component.mysql.utils.TreeNodeSqlUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public abstract class BaseTreeController<QueryCondition, DTO extends IConverter<Entity>, Entity> extends BaseController<QueryCondition, DTO, Entity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取所有相关的表ID列表
     *
     * @param selectSql 查询条件
     * @return java.util.List<java.lang.String>
     */
    private List<String> getTableIdList(String selectSql) {
        List<String> list = CollUtil.newArrayList();
        Class<Entity> supperClass = getSupperClass();
        if (Objects.isNull(supperClass)) {
            return list;
        }

        String sql = TreeNodeSqlUtils.buildSql(selectSql, getSupperClass());
        List<String> selectList = jdbcTemplate.queryForList(sql, String.class);
        HashSet<String> idSet = CollUtil.newHashSet();
        for (String str : selectList) {
            idSet.addAll(CollUtil.newArrayList(str.split(":")));
        }
        return CollUtil.newArrayList(idSet);
    }

    /**
     * 查询SQL
     *
     * @param queryCondition 查询条件
     * @return java.lang.String
     */
    private String selectLikeSql(QueryCondition queryCondition) {
        StringBuilder builder = new StringBuilder();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof String) {
                builder.append(key).append(" LIKE '%").append(value).append("%'");
            } else {
                builder.append(key).append(" = ").append(value);
            }
        });
        return builder.toString();
    }

    /**
     * 查询SQL
     *
     * @param queryCondition 查询条件
     * @return java.lang.String
     */
    private String selectSql(QueryCondition queryCondition) {
        StringBuilder builder = new StringBuilder();
        EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition).forEach((key, value) -> {
            if (value instanceof String) {
                builder.append(key).append(" = '").append(value).append("'");
            } else {
                builder.append(key).append(" = ").append(value);
            }
        });
        return builder.toString();
    }


    /**
     * 构建Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildTreeWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        Map<String, Object> allTableFieldPropertyValue = EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition);
        if (allTableFieldPropertyValue.size() == 0) {
            return query;
        }

        Class<Entity> supperClass = getSupperClass();
        if (Objects.isNull(supperClass)) {
            return query;
        }

        List<String> tableIdList = this.getTableIdList(selectSql(queryCondition));

        if (CollUtil.isEmpty(tableIdList)) {
            tableIdList.add("-1");
        }

        return query.in(TreeNodeSqlUtils.getTableId(supperClass), tableIdList);
    }

    /**
     * 构建Wrapper
     *
     * @param queryCondition 参数对象
     * @return com.baomidou.mybatisplus.core.conditions.Wrapper<Entity>
     */
    public Wrapper<Entity> buildLikeTreeWrapper(QueryCondition queryCondition) {
        QueryWrapper<Entity> query = Wrappers.query();
        this.defaultOrder(query);
        Map<String, Object> allTableFieldPropertyValue = EntityPropertyUtils.getAllTableFieldPropertyValue(queryCondition);
        if (allTableFieldPropertyValue.size() == 0) {
            return query;
        }

        Class<Entity> supperClass = getSupperClass();
        if (Objects.isNull(supperClass)) {
            return query;
        }

        List<String> tableIdList = this.getTableIdList(selectLikeSql(queryCondition));

        if (CollUtil.isEmpty(tableIdList)) {
            tableIdList.add("-1");
        }

        query.in(TreeNodeSqlUtils.getTableId(supperClass), tableIdList);
        return query;
    }


    /**
     * 获取树形结构列表
     *
     * @param queryCondition 查询条件
     * @return java.util.List<Entity>
     */
    public List<Entity> findTree(QueryCondition queryCondition) {
        return this.getService().tree(this.getService().list(this.buildTreeWrapper(queryCondition)));
    }

    /**
     * 获取树形列表信息
     *
     * @param queryCondition 实体对象
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/tree")
    @ApiOperation(value = "统一获取树形信息接口")
    public HttpResult<List<Entity>> tree(@RequestBody QueryCondition queryCondition) {
        return HttpResult.ok(this.findTree(queryCondition));
    }

    /**
     * 模糊获取树形结构列表
     *
     * @param queryCondition 查询条件
     * @return java.util.List<Entity>
     */
    public List<Entity> findLikeTree(QueryCondition queryCondition) {
        return this.getService().tree(this.getService().list(this.buildLikeTreeWrapper(queryCondition)));
    }

    /**
     * 模糊获取树形列表信息
     *
     * @param queryCondition 实体对象
     * @return reactor.core.publisher.Mono<com.jiayou.spd.common.core.entity.HttpResult>
     */
    @PostMapping("base/like/tree")
    @ApiOperation(value = "统一模糊获取树形信息接口")
    public HttpResult<List<Entity>> likeTree(@RequestBody QueryCondition queryCondition) {
        return HttpResult.ok(this.findLikeTree(queryCondition));
    }

}
