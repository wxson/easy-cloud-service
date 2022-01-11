package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author Fast Java
 * @date 2021-03-16
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_user")
public class UserDO implements IConverter<UserVO> {
    /**
     * 文档ID
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身
     * 一个企业、一个单位或一所学校只能有一个租户
     */
    private String tenantId;
    /**
     * 微信union Id
     */
    private String unionId;
    /**
     * 苹果ID
     */
    private String appleId;
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
    private String district;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
     */
    private Integer sex;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 身份证
     */
    private String identity;
    /**
     * 电话
     */
    private String tel;
    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;
    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer deleted;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新人员
     */
    private String updaterAt;
    /**
     * 更新时间
     */
    private String updateAt;
}