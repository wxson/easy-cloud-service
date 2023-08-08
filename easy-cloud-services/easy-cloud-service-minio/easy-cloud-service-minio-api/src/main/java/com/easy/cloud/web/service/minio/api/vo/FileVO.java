package com.easy.cloud.web.service.minio.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * File展示数据
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {

  /**
   * 文档ID
   */
  private String id;
  /**
   * 文档名称
   */
  private String name;
  /**
   * 文档地址
   */
  private String url;
  /**
   * 文档地址
   */
  private Long size;
  /**
   * 是否为目录
   */
  private Boolean isDir;

}