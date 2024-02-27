package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.upms.api.enums.TenantTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Tenant 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EnableTenant
@EnableLogic
@Table(name = "db_tenant")
public class TenantDO extends BaseEntity {

  /**
   * 租户名称
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '租户名称'")
  private String name;

  /**
   * 租户类型（企业和个人）
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '租户类型'")
  private TenantTypeEnum type;
  /**
   * 国家
   */
  @Column(columnDefinition = "VARCHAR(8) COMMENT '国家'")
  private String country;
  /**
   * 省
   */
  @Column(columnDefinition = "VARCHAR(8) COMMENT '省'")
  private String province;
  /**
   * 市
   */
  @Column(columnDefinition = "VARCHAR(8) COMMENT '市'")
  private String city;
  /**
   * 区域
   */
  @Column(columnDefinition = "VARCHAR(8) COMMENT '区域'")
  private String region;
  /**
   * 排序
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '排序'")
  private Integer sort;
  /**
   * 账户ID
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '账户ID'")
  private String accountId;
  /**
   * 负责人
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '负责人'")
  private String leader;
  /**
   * 联系电话
   */
  @Column(columnDefinition = "VARCHAR(11) COMMENT '联系电话'")
  private String tel;
  /**
   * 邮箱
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '邮箱'")
  private String email;

  /**
   * 开始时间
   */
  @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
  @DateTimeFormat(pattern = DateTimeConstants.DEFAULT_FORMAT)
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '开始时间'")
  protected Date startDate;


  /**
   * 结束时间
   */
  @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
  @DateTimeFormat(pattern = DateTimeConstants.DEFAULT_FORMAT)
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '结束时间'")
  protected Date endDate;
  /**
   * 描述
   */
  @Column(columnDefinition = "VARCHAR(225) COMMENT '描述'")
  private String remark;
}