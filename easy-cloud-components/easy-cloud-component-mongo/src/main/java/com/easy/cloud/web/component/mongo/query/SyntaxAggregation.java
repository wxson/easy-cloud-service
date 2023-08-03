package com.easy.cloud.web.component.mongo.query;

import org.springframework.data.mongodb.core.aggregation.Aggregation;

/**
 * @author GR
 * @date 2020-4-29 18:02
 */
public class SyntaxAggregation extends Aggregation {

  public static SyntaxAggregationOperation expression(String jsonOperation) {
    return new SyntaxAggregationOperation(jsonOperation);
  }
}
