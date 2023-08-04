package com.easy.cloud.web.module.dict.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Dict请求数据
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DictDTO {

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
  private String describe;
  /**
   *
   */
  private String type;
}