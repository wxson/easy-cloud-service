package com.easy.cloud.web.component.mongo.query;

import com.easy.cloud.web.component.mongo.utils.MongodbColumUtil;
import com.easy.cloud.web.component.mongo.SFunction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

/**
 * 支持Lambda 语法Sort
 *
 * @author GR
 * @date 2020-11-13 15:42
 */
public class LambdaSortWrapper extends Sort {

  protected LambdaSortWrapper(List<Order> orders) {
    super(orders);
  }

  /**
   * 支持 Lambda 语法
   *
   * @param properties Lambda表达式
   * @return org.springframework.data.mongodb.core.query.Criteria
   */
  @SafeVarargs
  public static <T> Sort by(SFunction<T, ?>... properties) {
    String[] propertiesStr = new String[properties.length];
    for (int index = 0; index < properties.length; index++) {
      propertiesStr[index] = MongodbColumUtil.getEntityClum(properties[index]);
    }
    Assert.notNull(properties, "Properties must not be null!");
    return properties.length == 0 ? unsorted() : Sort.by(DEFAULT_DIRECTION, propertiesStr);
  }

  /**
   * 支持 Lambda 语法
   *
   * @param properties Lambda表达式
   * @return org.springframework.data.mongodb.core.query.Criteria
   */
  @SafeVarargs
  public static <T> Sort by(Direction direction, SFunction<T, ?>... properties) {
    Assert.notNull(direction, "Direction must not be null!");
    Assert.notNull(properties, "Properties must not be null!");
    Assert.isTrue(properties.length > 0, "At least one property must be given!");
    return Sort.by(Arrays.stream(properties)
        .map((it) -> new Order(direction, MongodbColumUtil.getEntityClum(it)))
        .collect(Collectors.toList()));
  }

}
