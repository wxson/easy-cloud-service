package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
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

/**
 * User 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
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
@Table(name = "db_user")
public class UserDO extends BaseEntity {

  /**
   * 用户名/用户账号
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户名/用户账号'")
  private String userName;
  /**
   * 登录密码
   */
  @Column(columnDefinition = "VARCHAR(225) NOT NULL COMMENT '登录密码'")
  private String password;
  /**
   * 用户名（真实名字）
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '用户名（真实名字）'")
  private String realName;
  /**
   * 昵称
   */
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '昵称'")
  private String nickName;
  /**
   * 部门ID
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '部门ID'")
  private String deptId;

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
   * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(32) DEFAULT 'MAN' COMMENT '性别： 0 未知的性别 1 男 2 女 9 未说明的性别'")
  private GenderEnum gender;
  /**
   * 身份证
   */
  @Column(columnDefinition = "VARCHAR(18) COMMENT '身份证'")
  private String idCard;
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
}