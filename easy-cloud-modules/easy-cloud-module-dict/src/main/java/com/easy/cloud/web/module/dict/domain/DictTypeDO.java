package com.easy.cloud.web.module.dict.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 字典类型表
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_dict_type")
@ApiModel(value = "字典类型数据", description = "字典类型数据")
public class DictTypeDO implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;
    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典名称不能为空")
    @ApiModelProperty(name = "name", value = "字典类型名称")
    private String name;
    /**
     * 字典类型字段，必须唯一，如性别：SEX;语言：LANGUAGE
     */
    @NotBlank(message = "字典名称字段不能为空")
    @ApiModelProperty(name = "field", value = "字典类型字段")
    private String field;
    /**
     * 排序
     */
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;
    /**
     * 操作者
     */
    @ApiModelProperty(name = "creatorAt", value = "操作者")
    private String creatorAt;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createAt", value = "创建时间")
    private String createAt;
}
