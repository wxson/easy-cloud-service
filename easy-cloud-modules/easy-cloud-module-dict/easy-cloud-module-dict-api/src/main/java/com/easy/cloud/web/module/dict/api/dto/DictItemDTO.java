package com.easy.cloud.web.module.dict.api.dto;

import javax.validation.constraints.NotBlank;
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
  private String id;
  /**
   * 字典类型
   */
  @NotBlank(message = "字典类型不能为空")
  private String dictType;
  /**
   * 字典标签
   */
  @NotBlank(message = "字典标签不能为空")
  private String label;
  /**
   * 字典值
   */
  @NotBlank(message = "字典值不能为空")
  private String value;
  /**
   *
   */
  private String remark;
}