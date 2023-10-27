package com.easy.cloud.web.component.mysql.factory;

import com.easy.cloud.web.component.core.funinterface.SFunction;
import com.easy.cloud.web.component.core.util.FieldUtils;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author GR
 * @date 2023/9/5 18:17
 */
public class SpecificationFactory {

  /**
   * 时区对象
   */
  private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");
  /**
   * 日期时间格式化对象
   */
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");

  private static <T> String fileName(SFunction<T, ?> function) {
    return FieldUtils.propertyName(function);
  }

  /**
   * 模糊匹配头部, like %?1
   *
   * @param function 实体中的字段名称
   * @param value    固定值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> likeStart(SFunction<T, ?> function, Object value) {
    return (root, query, cb) -> cb.like(root.get(fileName(function)), "%" + value);
  }

  /**
   * 模糊匹配尾部, like ?1%
   *
   * @param function 实体中的字段名称
   * @param value    固定值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> likeEnd(SFunction<T, ?> function, Object value) {
    return (root, query, cb) -> cb.like(root.get(fileName(function)), value + "%");
  }

  /**
   * 完全模糊匹配 ， like %?1%
   *
   * @param function 实体中的字段名称
   * @param value    固定值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> like(SFunction<T, ?> function, Object value) {
    return likeBuild(function, "%" + value + "%");
  }

  private static <T> Specification<T> likeBuild(SFunction<T, ?> function, Object value) {
    return (root, query, cb) -> cb.like(root.get(fileName(function)), "%" + value + "%");
  }

  /**
   * 任意值相等比较
   *
   * @param function 实体中的字段名称
   * @param value    比较值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> eq(SFunction<T, ?> function, Object value) {
    return (root, query, cb) -> cb.equal(root.get(fileName(function)), value);
  }

  /**
   * 比较日期区间
   *
   * @param function 实体中的字段名称
   * @param min      最小日期值
   * @param max      最大日期值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> betweenDate(SFunction<T, ?> function, Date min, Date max) {
    LocalDateTime lmin = LocalDateTime.ofInstant(min.toInstant(), ZONE_OFFSET);
    LocalDateTime lmax = LocalDateTime.ofInstant(max.toInstant(), ZONE_OFFSET);
    return (root, query, cb) -> cb
        .between(root.get(fileName(function)).as(String.class), DATE_TIME_FORMATTER.format(lmin),
            DATE_TIME_FORMATTER.format(lmax));
  }

  /**
   * 比较任意值的区间
   *
   * @param function 实体中的字段名称
   * @param min      最小值
   * @param max      最大值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T, V extends Comparable> Specification<T> between(SFunction<T, ?> function, V min,
      V max) {
    return (root, query, cb) -> cb.between(root.get(fileName(function)), min, max);
  }


  /**
   * 数值大于比较
   *
   * @param function 实体中的字段名称
   * @param value    比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T, V extends Number> Specification<T> gt(SFunction<T, ?> function, V value) {
    return (root, query, cb) -> cb.gt(root.get(fileName(function)).as(Number.class), value);
  }


  /**
   * 数值大于等于比较
   *
   * @param function 实体中的字段名称
   * @param value    比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T, V extends Comparable> Specification<T> gte(SFunction<T, ?> function, V value) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(fileName(function)), value);
  }

  /**
   * 数值小于比较
   *
   * @param function 实体中的字段名称
   * @param value    比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Number> Specification<T> lt(SFunction<T, ?> function, T value) {
    return (root, query, cb) -> cb.lt(root.get(fileName(function)).as(Number.class), value);
  }

  /**
   * 数值小于等于比较
   *
   * @param function 实体中的字段名称
   * @param value    比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T, V extends Comparable> Specification<T> lte(SFunction<T, ?> function, V value) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(fileName(function)), value);
  }

  /**
   * 字段为null条件
   *
   * @param function 实体中的字段名称
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> isNull(SFunction<T, ?> function) {
    return (root, query, cb) -> cb.isNull(root.get(fileName(function)));
  }

  /**
   * 字段不为null条件
   *
   * @param function 实体中的字段名称
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> isNotNull(SFunction<T, ?> function) {
    return (root, query, cb) -> cb.isNotNull(root.get(fileName(function)));
  }

  /**
   * in 条件
   *
   * @param function
   * @param values
   * @return
   */
  public static <T> Specification<T> in(SFunction<T, ?> function, Object... values) {
    return (root, query, cb) -> root.get(fileName(function)).in(values);
  }

  /**
   * 模糊匹配头部, like %?1
   *
   * @param fieldName 实体中的字段名称
   * @param value     固定值
   * @return 查询条件的封装对象
   */
  public static <T> Specification<T> likeStart(String fieldName, Object value) {
    return (root, query, cb) -> cb.like(root.get(fieldName), "%" + value);
  }

  /**
   * 模糊匹配尾部, like ?1%
   *
   * @param fieldName 实体中的字段名称
   * @param value     固定值
   * @return 查询条件的封装对象
   */
  public static Specification likeEnd(String fieldName, Object value) {
    return (root, query, cb) -> cb.like(root.get(fieldName), value + "%");
  }

  /**
   * 完全模糊匹配 ， like %?1%
   *
   * @param fieldName 实体中的字段名称
   * @param value     固定值
   * @return 查询条件的封装对象
   */
  public static Specification like(String fieldName, Object value) {
    return likeBuild(fieldName, "%" + value + "%");
  }

  private static Specification likeBuild(String fieldName, Object value) {
    return (root, query, cb) -> cb.like(root.get(fieldName), "%" + value + "%");
  }

  /**
   * 任意值相等比较
   *
   * @param fieldName 实体中的字段名称
   * @param value     比较值
   * @return 查询条件的封装对象
   */
  public static <T> Specification eq(String fieldName, Object value) {
    return (root, query, cb) -> cb.equal(root.get(fieldName), value);
  }

  /**
   * 比较日期区间
   *
   * @param fieldName 实体中的字段名称
   * @param min       最小日期值
   * @param max       最大日期值
   * @return 查询条件的封装对象
   */
  public static Specification betweenDate(String fieldName, Date min, Date max) {
    LocalDateTime lmin = LocalDateTime.ofInstant(min.toInstant(), ZONE_OFFSET);
    LocalDateTime lmax = LocalDateTime.ofInstant(max.toInstant(), ZONE_OFFSET);
    return (root, query, cb) -> cb
        .between(root.get(fieldName).as(String.class), DATE_TIME_FORMATTER.format(lmin),
            DATE_TIME_FORMATTER.format(lmax));
  }

  /**
   * 比较任意值的区间
   *
   * @param fieldName 实体中的字段名称
   * @param min       最小值
   * @param max       最大值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Comparable> Specification between(String fieldName, T min, T max) {
    return (root, query, cb) -> cb.between(root.get(fieldName), min, max);
  }


  /**
   * 数值大于比较
   *
   * @param fieldName 实体中的字段名称
   * @param value     比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Number> Specification gt(String fieldName, T value) {
    return (root, query, cb) -> cb.gt(root.get(fieldName).as(Number.class), value);
  }


  /**
   * 数值大于等于比较
   *
   * @param fieldName 实体中的字段名称
   * @param value     比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Comparable> Specification gte(String fieldName, T value) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(fieldName), value);
  }

  /**
   * 数值小于比较
   *
   * @param fieldName 实体中的字段名称
   * @param value     比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Number> Specification lt(String fieldName, T value) {
    return (root, query, cb) -> cb.lt(root.get(fieldName).as(Number.class), value);
  }

  /**
   * 数值小于等于比较
   *
   * @param fieldName 实体中的字段名称
   * @param value     比较值
   * @param <T>
   * @return 查询条件的封装对象
   */
  public static <T extends Comparable> Specification lte(String fieldName, T value) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(fieldName), value);
  }

  /**
   * 字段为null条件
   *
   * @param fieldName 实体中的字段名称
   * @return 查询条件的封装对象
   */
  public static Specification isNull(String fieldName) {
    return (root, query, cb) -> cb.isNull(root.get(fieldName));
  }

  /**
   * 字段不为null条件
   *
   * @param fieldName 实体中的字段名称
   * @return 查询条件的封装对象
   */
  public static Specification isNotNull(String fieldName) {
    return (root, query, cb) -> cb.isNotNull(root.get(fieldName));
  }

  /**
   * in 条件
   *
   * @param fieldName
   * @param values
   * @return
   */
  public static Specification in(String fieldName, Object... values) {
    return (root, query, cb) -> root.get(fieldName).in(values);
  }

}
