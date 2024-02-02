package com.easy.cloud.web.module.sms.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.module.sms.api.enums.SmsSendStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Sms 持久类
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_sms")
public class SmsDO extends BaseEntity {

    /**
     * 短信验证码
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '短信验证码'")
    private String code;
    /**
     * 电话
     */
    @Column(columnDefinition = "VARCHAR(11) NOT NULL COMMENT '电话'")
    private String tel;
    /**
     * 短信内容
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '短信内容'")
    private String content;
    /**
     * 验证码是否已使用，默认未使用
     */
    @Column(columnDefinition = "TINYINT(1) NOT NULL DEFAULT '0' COMMENT '验证码是否已使用，默认未使用'")
    private Boolean used;
    /**
     * 短信备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '短信备注'")
    private String remark;
    /**
     * 短信发送状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'UN_SEND' COMMENT '短信发送状态'")
    private SmsSendStatusEnum sendStatus;
}