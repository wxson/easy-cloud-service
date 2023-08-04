package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * User 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_user")
public class UserDO implements IConverter {

  /**
   * 文档ID
   */
  @Id
  private String id;
  /**
   * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '租户ID'")
  private String tenantId;
  /**
   * 微信union Id
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '微信union Id'")
  private String unionId;
  /**
   * 苹果ID
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '苹果ID'")
  private String appleId;
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
   * 昵称
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '昵称'")
  private String nickName;
  /**
   * 账号
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '账号'")
  private String account;
  /**
   * 密码
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '密码'")
  private String password;
  /**
   * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "TINYINT NOT NULL COMMENT '性别： 0 未知的性别 1 男 2 女 9 未说明的性别'")
  private Integer sex;
  /**
   * 用户名
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '密码'")
  private String userName;
  /**
   * 身份证
   */
  @Column(columnDefinition = "VARCHAR(18) COMMENT '身份证'")
  private String identity;
  /**
   * 电话
   */
  @Column(columnDefinition = "VARCHAR(11) COMMENT '电话'")
  private String tel;
  /**
   * 头像
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '头像'")
  private String avatar;
  /**
   * 邮箱
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '邮箱'")
  private String email;
  /**
   * 状态 0 启用 1 禁用
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'START_STATUS' COMMENT '状态'")
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'UN_DELETED' COMMENT '是否删除'")
  private DeletedEnum deleted;
  /**
   * 创建用户
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建用户'")
  private String createBy;
  /**
   * 创建时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建时间'")
  private String createAt;
  /**
   * 更新人员
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新人员'")
  private String updateBy;
  /**
   * 更新时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新时间'")
  private String updateAt;
}