package com.easy.cloud.web.module.sms.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.module.sms.domain.vo.SmsSendRecordVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 短信发送记录
 *
 * @author GR
 * @date 2021-12-9 18:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName("db_send_sms_record")
public class SmsSendRecordDO implements IConverter<SmsSendRecordVO> {
    /**
     * 文档ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 短信验证码
     */
    private String code;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
}
