package com.easy.cloud.web.module.dict.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DictItem请求数据
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DictItemDTO {

  /**
   * 文档ID
   */
  private Long id;
  /**
   *
   */
  private String name;
  /**
   *
   */
  private String remark;
  /**
   *
   */
  private Integer dictId;
  /**
   *
   */
  private String describe;
  /**
   *
   */
  private String value;
  /**
   *
   */
  private String dictType;
}