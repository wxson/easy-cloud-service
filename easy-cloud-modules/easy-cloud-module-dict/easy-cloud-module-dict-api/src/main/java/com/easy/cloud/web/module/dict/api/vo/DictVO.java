package com.easy.cloud.web.module.dict.api.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Dict展示数据
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DictVO {

  /**
   * 文档ID
   */
  private String id;
  /**
   *
   */
  private String name;
  /**
   *
   */
  private String type;
  /**
   *
   */
  private String remark;
  /**
   * 创建用户
   */
  private String createBy;
  /**
   * 创建时间
   */
  private String createAt;

  /**
   * 字典项
   */
  private List<DictItemVO> items;
}