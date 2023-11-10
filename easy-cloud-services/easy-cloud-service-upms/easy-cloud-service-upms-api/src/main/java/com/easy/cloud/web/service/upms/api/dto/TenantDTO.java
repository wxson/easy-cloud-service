package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.TenantTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Tenant请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TenantDTO implements IConverter {

  /**
   * 文档ID
   */
  private String id;

  /**
   * 租户名称
   */
  private String name;

  /**
   * 租户类型（企业和个人）
   */
  private TenantTypeEnum type;
  /**
   * 国家
   */
  private String country;
  /**
   * 省
   */
  private String province;
  /**
   * 市
   */
  private String city;
  /**
   * 区域
   */
  private String region;
  /**
   * 排序
   */
  private Integer sort;
  /**
   * 负责人
   */
  private String leader;
  /**
   * 联系电话
   */
  private String tel;
  /**
   * 邮箱
   */
  private String email;

  /**
   * 开始时间
   */
  protected Date startDate;

  /**
   * 结束时间
   */
  protected Date endDate;

  /**
   * 描述
   */
  private String remark;
  /**
   * 状态 0 启用 1 禁用
   */
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  private DeletedEnum deleted;
}