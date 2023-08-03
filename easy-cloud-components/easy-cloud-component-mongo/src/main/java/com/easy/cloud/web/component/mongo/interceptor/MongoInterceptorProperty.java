package com.easy.cloud.web.component.mongo.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author GR
 * @date 2023/8/3 11:05
 */
@Component
@RefreshScope
public class MongoInterceptorProperty {

  /**
   * 创建人
   */
  @Value(value = "${mongo.auto.filed.creatorAt:creatorAt}")
  public String CREATOR_AT_FILED = "creatorAt";
  /**
   * 创建时间
   */
  @Value(value = "${mongo.auto.filed.createAt:createAt}")
  public String CREATE_AT_FILED = "createAt";
  /**
   * 更新人
   */
  @Value(value = "${mongo.auto.filed.updatorAt:updatorAt}")
  public String UPDATOR_AT_FILED = "updatorAt";
  /**
   * 更新时间
   */
  @Value(value = "${mongo.auto.filed.updateAt:updateAt}")
  public String UPDATE_AT_FILED = "updateAt";

}
