package com.easy.cloud.web.module.dict.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.dict.domain.DictTypeDO;
import com.easy.cloud.web.module.dict.service.IDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author GR
 * @date 2021-11-25 12:49
 */
@Slf4j
@RestController
@RequestMapping("dict/type")
@AllArgsConstructor
@Api(value = "字典类型管理", tags = "字典类型管理")
public class DictTypeController {

    private final IDictTypeService dictTypeService;

    /**
     * 新增
     *
     * @param dictTypeDO 数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("save")
    @ApiOperation(value = "新增字典类型")
    public HttpResult<Boolean> save(@Validated @RequestBody DictTypeDO dictTypeDO) {
        if (StrUtil.isBlank(dictTypeDO.getName())) {
            throw new BusinessException("字典类型名称不能为空");
        }

        if (StrUtil.isBlank(dictTypeDO.getField())) {
            throw new BusinessException("字典类型字段不能为空");
        }

        // 统一小写
        dictTypeDO.setField(dictTypeDO.getField().toLowerCase());

        List<DictTypeDO> dictTypeDOList = dictTypeService.list(Wrappers.<DictTypeDO>lambdaQuery().eq(DictTypeDO::getName, dictTypeDO.getName()));
        if (dictTypeDOList.size() > 0) {
            throw new BusinessException("当前字典类型名称已存在");
        }
        return HttpResult.ok(dictTypeService.save(dictTypeDO));
    }


    /**
     * 新增
     *
     * @param dictTypeDO 数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("update")
    @ApiOperation(value = "更新字典类型")
    public HttpResult<Boolean> update(@Validated @RequestBody DictTypeDO dictTypeDO) {
        if (Objects.isNull(dictTypeDO.getId())) {
            throw new BusinessException("当前字典类型ID不存在");
        }

        if (StrUtil.isBlank(dictTypeDO.getName())) {
            throw new BusinessException("字典类型名称不能为空");
        }


        if (StrUtil.isBlank(dictTypeDO.getField())) {
            throw new BusinessException("字典类型字段不能为空");
        }

        return HttpResult.ok(dictTypeService.saveOrUpdate(dictTypeDO));
    }

    /**
     * 移除
     *
     * @param id id
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @GetMapping("remove/{id}")
    @ApiOperation(value = "根据ID移除字典类型")
    public HttpResult<Boolean> remove(@PathVariable Integer id) {
        return HttpResult.ok(dictTypeService.removeById(id));
    }

    /**
     * 列表
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @GetMapping("list")
    @ApiOperation(value = "获取字典列表")
    public HttpResult<List<DictTypeDO>> list() {
        return HttpResult.ok(dictTypeService.list());
    }
}
