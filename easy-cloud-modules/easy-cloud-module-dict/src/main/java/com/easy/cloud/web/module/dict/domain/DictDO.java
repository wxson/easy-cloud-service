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
 * 字典表
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_dict")
@ApiModel(value = "字典数据", description = "字典数据")
public class DictDO implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "字典名称不能为空")
    @ApiModelProperty(name = "name", value = "名称")
    private String name;
    /**
     * 唯一标识
     */
    @ApiModelProperty(name = "value", value = "唯一标识")
    private Integer value;
    /**
     * 字典类型字段，对应dictType的field
     * 大写字段名
     */
    @NotBlank(message = "字典类型字段不能为空")
    @ApiModelProperty(name = "dictType", value = "字典类型字段")
    private String dictType;
    /**
     * 排序
     */
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;
    /**
     * 操作者
     */
    @ApiModelProperty(name = "creatorAt", value = "操作人")
    private String creatorAt;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createAt", value = "创建时间")
    private String createAt;
}
