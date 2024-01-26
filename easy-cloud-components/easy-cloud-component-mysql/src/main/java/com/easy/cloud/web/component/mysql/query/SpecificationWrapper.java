package com.easy.cloud.web.component.mysql.query;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.core.funinterface.SFunction;
import com.easy.cloud.web.component.core.util.FieldUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SpecificationWrapper Jpa 查询
 *
 * <p>语法说明：</p>
 * <p>基本语法：SpecificationWrapper.where(User::getName,"admin").like(User::getNickName,"Ec")</p>
 *
 * @author GR
 * @date 2024/1/23 15:05
 */
public class SpecificationWrapper implements Specification {

    /**
     * 查询条件
     */
    private LinkedHashMap<QueryOperator, QueryCondition> queryConditions;
    /**
     * 排序
     */
    private LinkedList<QueryCondition> orderConditions;
    /**
     * 分组
     */
    private LinkedList<String> groupByKeys;

    public SpecificationWrapper() {
        this.queryConditions = new LinkedHashMap<>();
    }

    protected <T> SpecificationWrapper(QueryOperator operator, SFunction<T, ?> func, Object obj) {
        this.queryConditions = new LinkedHashMap<>();
        this.addQueryCondition(operator, func, obj);
    }

    /**
     * 获取字段
     *
     * @param func func
     * @return java.lang.String
     */
    private <T> String getKey(SFunction<T, ?> func) {
        return FieldUtils.propertyName(func);
    }

    /**
     * 添加查询条件
     *
     * @param operator 操作类型
     * @param func     func
     * @param obj      obj
     */
    private <T> SpecificationWrapper addQueryCondition(QueryOperator operator, SFunction<T, ?> func, Object obj) {
        this.queryConditions.put(operator, new QueryCondition(this.getKey(func), obj));
        return this;
    }

    /**
     * 添加排序条件
     *
     * @param func      func
     * @param direction Sort.Direction
     */
    private <T> void addOrderCondition(SFunction<T, ?> func, Sort.Direction direction) {
        // 初始化
        if (CollUtil.isEmpty(orderConditions)) {
            this.orderConditions = new LinkedList<>();
        }
        // 创建排序条件
        QueryCondition queryCondition = new QueryCondition(this.getKey(func), null);
        queryCondition.direction = direction;
        this.orderConditions.add(queryCondition);
    }

    /**
     * query
     *
     * @return
     */
    public static SpecificationWrapper query() {
        return new SpecificationWrapper();
    }

    /**
     * where
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public static <T> SpecificationWrapper where(SFunction<T, ?> func, Object obj) {
        return new SpecificationWrapper(QueryOperator.EQ, func, obj);
    }

    /**
     * where
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public static <T> SpecificationWrapper where(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? new SpecificationWrapper(QueryOperator.EQ, func, obj) : new SpecificationWrapper();
    }

    /**
     * 等于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper eq(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.EQ, func, obj);
        return this;
    }

    /**
     * 等于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper eq(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.EQ, func, obj) : this;
    }

    /**
     * 不等于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper ne(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.NE, func, obj);
        return this;
    }

    /**
     * 不等于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper ne(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.NE, func, obj) : this;
    }

    /**
     * 大于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper gt(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.GT, func, obj);
        return this;
    }

    /**
     * 大于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper gt(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.GT, func, obj) : this;
    }

    /**
     * 大于等于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper gte(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.GE, func, obj);
        return this;
    }

    /**
     * 大于等于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper gte(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.GE, func, obj) : this;
    }

    /**
     * 小于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper lt(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.LT, func, obj);
        return this;
    }

    /**
     * 小于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper lt(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.LT, func, obj) : this;
    }

    /**
     * 小于等于
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper lte(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.LE, func, obj);
        return this;
    }

    /**
     * 小于等于
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper lte(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.LE, func, obj) : this;
    }

    /**
     * 模糊查询 LIKE
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper like(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.LIKE, func, "%" + obj + "%");
        return this;
    }

    /**
     * 模糊查询 LIKE
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper like(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.LIKE, func, "%" + obj + "%") : this;
    }

    /**
     * 模糊查询 LEFT LIKE
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper leftLike(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.LIKE, func, "%" + obj);
        return this;
    }

    /**
     * 模糊查询 LEFT LIKE
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper leftLike(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.LIKE, func, "%" + obj) : this;
    }

    /**
     * 模糊查询 LEFT LIKE
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper rightLike(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.LIKE, func, obj + "%");
        return this;
    }

    /**
     * 模糊查询 LEFT LIKE
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper rightLike(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.LIKE, func, obj + "%") : this;
    }

    /**
     * 模糊查询 NOT LIKE
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper notLike(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.NOT_LIKE, func, "%" + obj + "%");
        return this;
    }

    /**
     * 模糊查询 NOT LIKE
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper notLike(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.NOT_LIKE, func, "%" + obj + "%") : this;
    }

    /**
     * IN 查询
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper in(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.IN, func, obj);
        return this;
    }

    /**
     * IN 查询
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper in(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.IN, func, obj) : this;
    }

    /**
     * NOT IN 查询
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper notIn(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.NOT_IN, func, obj);
        return this;
    }

    /**
     * NOT IN 查询
     *
     * @param bool 是否添加
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper notIn(Boolean bool, SFunction<T, ?> func, Object obj) {
        return bool ? this.addQueryCondition(QueryOperator.NOT_IN, func, obj) : this;
    }

    /**
     * isNulL 查询
     *
     * @param func func
     * @return
     */
    public <T> SpecificationWrapper isNull(SFunction<T, ?> func) {
        this.addQueryCondition(QueryOperator.IS_NULL, func, null);
        return this;
    }

    /**
     * isNotNull 查询
     *
     * @param func func
     * @return
     */
    public <T> SpecificationWrapper isNotNull(SFunction<T, ?> func) {
        this.addQueryCondition(QueryOperator.IS_NOT_NULL, func, null);
        return this;
    }

    /**
     * BETWEEN 查询
     *
     * @param func func
     * @param min  min
     * @param max  max
     * @return
     */
    public <T> SpecificationWrapper between(SFunction<T, ?> func, Object min, Object max) {
        QueryCondition queryCondition = new QueryCondition(this.getKey(func), null);
        queryCondition.minObj = min;
        queryCondition.maxObj = max;
        this.queryConditions.put(QueryOperator.BETWEEN, queryCondition);
        return this;
    }

    /**
     * NOT_BETWEEN 查询
     *
     * @param func func
     * @param min  min
     * @param max  max
     * @return
     */
    public <T> SpecificationWrapper notBetween(SFunction<T, ?> func, Object min, Object max) {
        QueryCondition queryCondition = new QueryCondition(this.getKey(func), null);
        queryCondition.minObj = min;
        queryCondition.maxObj = max;
        this.queryConditions.put(QueryOperator.NOT_BETWEEN, queryCondition);
        return this;
    }

    /**
     * GROUP_BY 查询
     *
     * @param funcs func
     * @return
     */
    public <T> SpecificationWrapper groupBy(SFunction<T, ?>... funcs) {
        if (CollUtil.isEmpty(groupByKeys)) {
            this.groupByKeys = new LinkedList<>();
        }
        this.groupByKeys.addAll(Arrays.stream(funcs).map(this::getKey).collect(Collectors.toList()));
        return this;
    }

    /**
     * ORDER_BY 查询，默认ASC升序
     *
     * @param funcs func
     * @return
     */
    public <T> SpecificationWrapper orderBy(Sort.Direction direction, SFunction<T, ?>... funcs) {
        for (SFunction<T, ?> func : funcs) {
            this.addOrderCondition(func, direction);
        }
        return this;
    }

    /**
     * ORDER_ASC 升序查询
     *
     * @param func func
     * @return
     */
    public <T> SpecificationWrapper orderAsc(SFunction<T, ?> func) {
        this.orderBy(Sort.Direction.ASC, func);
        return this;
    }

    /**
     * ORDER_DESC 降序查询
     *
     * @param func func
     * @return
     */
    public <T> SpecificationWrapper orderDesc(SFunction<T, ?> func) {
        this.orderBy(Sort.Direction.DESC, func);
        return this;
    }

    /**
     * ORDER_DESC 查询
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper exists(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.EXISTS, func, obj);
        return this;
    }

    /**
     * NOT_EXISTS 查询
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper notExists(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.NOT_EXISTS, func, obj);
        return this;
    }

    /**
     * or
     *
     * @param func func
     * @param obj  obj
     * @return
     */
    public <T> SpecificationWrapper or(SFunction<T, ?> func, Object obj) {
        this.addQueryCondition(QueryOperator.OR, func, obj);
        return this;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        // 基本数据
        List<Predicate> predicates = this.queryConditions.entrySet().stream()
                // 过滤掉分组
                .filter(entry -> QueryOperator.GROUP_BY != entry.getKey())
                .map(queryConditionEntry -> {
                    QueryCondition queryCondition = queryConditionEntry.getValue();
                    switch (queryConditionEntry.getKey()) {
                        case EQ:
                            return cb.equal(root.get(queryCondition.key), queryCondition.value);
                        case NE:
                            return cb.notEqual(root.get(queryCondition.key), queryCondition.value);
                        case GT:
                            return cb.gt(root.get(queryCondition.key).as(Number.class), (Number) queryCondition.value);
                        case GE:
                            return cb.ge(root.get(queryCondition.key).as(Number.class), (Number) queryCondition.value);
                        case LT:
                            return cb.lt(root.get(queryCondition.key).as(Number.class), (Number) queryCondition.value);
                        case LE:
                            return cb.le(root.get(queryCondition.key).as(Number.class), (Number) queryCondition.value);
                        case LIKE:
                            return cb.like(root.get(queryCondition.key).as(String.class), (String) queryCondition.value);
                        case NOT_LIKE:
                            return cb.notLike(root.get(queryCondition.key).as(String.class), (String) queryCondition.value);
                        case IN:
                            return root.get(queryCondition.key).in(queryCondition.value);
                        case NOT_IN:
                            return cb.not(root.get(queryCondition.key).in(queryCondition.value));
                        case IS_NULL:
                            return cb.isNull(root.get(queryCondition.key));
                        case IS_NOT_NULL:
                            return cb.isNotNull(root.get(queryCondition.key));
                        case BETWEEN:
                            return cb.between(root.get(queryCondition.key), (String) queryCondition.minObj, (String) queryCondition.maxObj);
                        case NOT_BETWEEN:
                            return cb.between(root.get(queryCondition.key), (String) queryCondition.minObj, (String) queryCondition.maxObj).not();
                        case JOIN:
                            return null;
                        case LEFT_JOIN:
                            return null;
                        case RIGHT_JOIN:
                            return null;
                        case EXISTS:
                            return null;
                        case NOT_EXISTS:
                            return null;
                        case OR:
                            return null;
                        default:
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
        // 查询数据
        if (Objects.nonNull(predicates) && predicates.size() > 0) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        // 分组
        if (Objects.nonNull(groupByKeys) && groupByKeys.size() > 0) {
            query.groupBy(groupByKeys.stream().map(root::get).collect(Collectors.toList()));
        }
        // 排序
        if (Objects.nonNull(orderConditions) && orderConditions.size() > 0) {
            query.orderBy((List<Order>) orderConditions.stream()
                    .map(queryCondition -> Sort.Direction.ASC == queryCondition.direction
                            ? cb.asc(root.get(queryCondition.key)) : cb.desc(root.get(queryCondition.key))));
        }
        return query.getRestriction();
    }

    /**
     * 操作
     */
    private enum QueryOperator {
        EQ, NE, GT, GE, LT, LE, LIKE, NOT_LIKE, IN, NOT_IN, IS_NULL, IS_NOT_NULL,
        BETWEEN, NOT_BETWEEN, JOIN, LEFT_JOIN, RIGHT_JOIN, GROUP_BY, EXISTS, NOT_EXISTS, OR
    }

    /**
     * 查询条件
     */
    private class QueryCondition {
        /**
         * 查询字段
         */
        String key;
        /**
         * 查询值
         */
        Object value;
        /**
         * 格式化
         */
        String format;

        /**
         * 最小值
         */
        Object minObj;

        /**
         * 最大值
         */
        Object maxObj;

        /**
         * 排序方式
         */
        Sort.Direction direction;

        public QueryCondition(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
