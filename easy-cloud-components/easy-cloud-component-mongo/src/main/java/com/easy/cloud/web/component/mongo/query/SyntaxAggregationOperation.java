package com.easy.cloud.web.component.mongo.query;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

/**
 * TODO 后续可重新实现FieldsExposingAggregationOperation 自定义语法聚合操作 使用注意：不能调用父级个相关方法，可能存在bug
 *
 * @author GR
 * @date 2020-4-29 15:51
 */
public class SyntaxAggregationOperation extends ProjectionOperation {

  private final String jsonOperation;

  public SyntaxAggregationOperation(String jsonOperation) {
    this.jsonOperation = jsonOperation;
  }

  @Override
  public Document toDocument(AggregationOperationContext aggregationOperationContext) {
    return aggregationOperationContext.getMappedObject(Document.parse(jsonOperation));
  }
}
