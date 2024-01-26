package com.easy.cloud.web.component.mysql.query;

import com.easy.cloud.web.component.core.funinterface.SFunction;
import com.easy.cloud.web.component.core.util.FieldUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SpecificationWrapper Jpa 查询
 *
 * <p>语法说明：</p>
 * <p>基本语法：SpecificationWrapper.where(User::getName,"admin").like(User::getNickName,"Ec")</p>
 * <p>OR语法：.or(SpecificationWrapper.query().eq(UserDO::getUserName, "11")</p>
 * <p>groupBy语法：.groupBy(UserDO::getStatus)</p>
 * <p>orderBy语法:.orderDesc(UserDO::getUserName)</p>
 * <p>join语法: TODO 未实现</p>
 *
 * @author GR
 * @date 2024/1/23 15:05
 */
public class SpecificationWrapper implements Specification {

    private SpecificationBuilder specificationBuilder;

    private Map<QueryOperator, List<SpecificationWrapper>> specificationWrapperMap;

    public SpecificationWrapper() {
        this.specificationBuilder = new SpecificationBuilder();
        this.specificationWrapperMap = new HashMap<>(1);
    }

    protected <T> SpecificationWrapper(QueryOperator operator, SFunction<T, ?> func, Object obj) {
        this.specificationBuilder = new SpecificationBuilder();
        this.specificationWrapperMap = new HashMap<>(1);
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
        this.specificationBuilder.getQueryConditions().add(new QueryCondition(operator, this.getKey(func), obj));
        return this;
    }

    /**
     * 添加排序条件
     *
     * @param func      func
     * @param direction Sort.Direction
     */
    private <T> void addOrderCondition(SFunction<T, ?> func, Sort.Direction direction) {
        // 创建排序条件
        QueryCondition queryCondition = new QueryCondition(null, this.getKey(func), null);
        queryCondition.direction = direction;
        this.specificationBuilder.getOrderConditions().add(queryCondition);
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
        QueryCondition queryCondition = new QueryCondition(QueryOperator.BETWEEN, this.getKey(func), null);
        queryCondition.minObj = min;
        queryCondition.maxObj = max;
        this.specificationBuilder.getQueryConditions().add(queryCondition);
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
        QueryCondition queryCondition = new QueryCondition(QueryOperator.NOT_BETWEEN, this.getKey(func), null);
        queryCondition.minObj = min;
        queryCondition.maxObj = max;
        this.specificationBuilder.getQueryConditions().add(queryCondition);
        return this;
    }

    /**
     * GROUP_BY 查询
     *
     * @param funcs func
     * @return
     */
    public <T> SpecificationWrapper groupBy(SFunction<T, ?>... funcs) {
        this.specificationBuilder.getGroupByKeys().addAll(Arrays.stream(funcs).map(this::getKey).collect(Collectors.toList()));
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

    /**
     * or
     *
     * @param specificationWrapper SpecificationWrapper
     * @return
     */
    public <T> SpecificationWrapper or(SpecificationWrapper specificationWrapper) {
        List<SpecificationWrapper> orDefault = this.specificationWrapperMap.getOrDefault(QueryOperator.OR, new LinkedList<>());
        orDefault.add(specificationWrapper);
        this.specificationWrapperMap.put(QueryOperator.OR, orDefault);
        return this;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        // 构建query
        this.buildQueryPredicate(root, query, cb);
        // 构建GroupBy
        this.buildGroupByPredicate(root, query, cb);
        // 构建OrderBy
        this.buildOrderByPredicate(root, query, cb);
        // 构建Join
        this.buildJoinPredicate(root, query, cb);

        return query.getRestriction();
    }

    /**
     * 查询条件
     *
     * @param root
     * @param query
     * @param cb
     * @return
     */
    private List<Predicate> buildBaseQueryPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        return this.specificationBuilder.getQueryConditions().stream()
                .map(queryCondition -> {
                    switch (queryCondition.operator) {
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
                        default:
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 查询条件
     *
     * @param root
     * @param query
     * @param cb
     * @return
     */
    private void buildQueryPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        // 基本数据
        List<Predicate> queryPredicates = this.buildBaseQueryPredicate(root, query, cb);
        // 查询数据
        List<Predicate> predicates = new ArrayList<>();
        // query
        if (!CollectionUtils.isEmpty(queryPredicates)) {
            predicates.add(cb.and(queryPredicates.toArray(new Predicate[queryPredicates.size()])));
        }

        // or
        if (this.specificationWrapperMap.containsKey(QueryOperator.OR)) {
            // OR Query
            List<Predicate> orPredicates = Optional.ofNullable(this.specificationWrapperMap.get(QueryOperator.OR))
                    .orElse(new LinkedList<>())
                    .stream()
                    .map(sw -> cb.and(sw.buildBaseQueryPredicate(root, query, cb).toArray(new Predicate[]{})))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orPredicates)) {
                predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
            }
        }

        if (!CollectionUtils.isEmpty(predicates)) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    /**
     * 构建GroupBy
     *
     * @param root
     * @param query
     * @param cb
     */
    private void buildGroupByPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        if (CollectionUtils.isEmpty(this.specificationBuilder.getGroupByKeys())) {
            return;
        }
        // 分组
        query.groupBy(this.specificationBuilder.getGroupByKeys().stream().map(root::get).collect(Collectors.toList()));
    }

    /**
     * 构建OrderBy
     *
     * @param root
     * @param query
     * @param cb
     */
    private void buildOrderByPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        if (CollectionUtils.isEmpty(this.specificationBuilder.getOrderConditions())) {
            return;
        }
        // 排序
        query.orderBy(this.specificationBuilder.getOrderConditions().stream()
                .map(queryCondition -> Sort.Direction.ASC == queryCondition.direction
                        ? cb.asc(root.get(queryCondition.key)) : cb.desc(root.get(queryCondition.key)))
                .collect(Collectors.toList()));
    }

    /**
     * TODO Join 查询
     *
     * @param root
     * @param query
     * @param cb
     */
    private void buildJoinPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

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
         * 类型
         */
        QueryOperator operator;
        /**
         * 查询字段
         */
        String key;
        /**
         * 查询值
         */
        Object value;

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

        public QueryCondition(QueryOperator operator, String key, Object value) {
            this.operator = operator;
            this.key = key;
            this.value = value;
        }
    }

    private class SpecificationBuilder {
        /**
         * 查询条件
         */
        private LinkedList<QueryCondition> queryConditions;
        /**
         * 排序
         */
        private LinkedList<QueryCondition> orderConditions;
        /**
         * 分组
         */
        private LinkedList<String> groupByKeys;

        public SpecificationBuilder() {
        }

        public LinkedList<QueryCondition> getQueryConditions() {
            if (CollectionUtils.isEmpty(this.queryConditions)) {
                this.queryConditions = new LinkedList<>();
            }
            return queryConditions;
        }

        public LinkedList<QueryCondition> getOrderConditions() {
            if (CollectionUtils.isEmpty(this.orderConditions)) {
                this.orderConditions = new LinkedList<>();
            }
            return orderConditions;
        }

        public LinkedList<String> getGroupByKeys() {
            if (CollectionUtils.isEmpty(this.groupByKeys)) {
                this.groupByKeys = new LinkedList<>();
            }
            return groupByKeys;
        }
    }
}
