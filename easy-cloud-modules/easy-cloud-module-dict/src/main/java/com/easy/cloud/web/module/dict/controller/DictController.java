package com.easy.cloud.web.module.dict.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.dict.domain.DictDO;
import com.easy.cloud.web.module.dict.service.IDictService;
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
@RequestMapping("dict")
@AllArgsConstructor
@Api(value = "字典管理", tags = "字典管理")
public class DictController {

    private final IDictService dictService;

    /**
     * 新增
     *
     * @param dictDO 数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("save")
    @ApiOperation(value = "新增字典")
    public HttpResult<Boolean> save(@Validated @RequestBody DictDO dictDO) {
        if (StrUtil.isBlank(dictDO.getName())) {
            throw new BusinessException("字典名称不能为空");
        }

        if (Objects.isNull(dictDO.getValue())) {
            throw new BusinessException("字典唯一标识不能为空");
        }

        if (StrUtil.isBlank(dictDO.getDictType())) {
            throw new BusinessException("字典类型不能为空");
        }

        List<DictDO> dictDOList = dictService.list(Wrappers.<DictDO>lambdaQuery().eq(DictDO::getDictType, dictDO.getDictType()));
        // 唯一标识是否重复
        long valueCount = dictDOList.stream().filter(dict -> dict.getValue().equals(dictDO.getValue())).count();
        if (valueCount > 0) {
            throw new BusinessException("当前字典唯一标识已存在");
        }

        long nameCount = dictDOList.stream().filter(dict -> dict.getName().equals(dictDO.getName())).count();
        if (nameCount > 0) {
            throw new BusinessException("当前字典名称已存在");
        }

        return HttpResult.ok(dictService.save(dictDO));
    }


    /**
     * 新增
     *
     * @param dictDO 数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("update")
    @ApiOperation(value = "更新字典")
    public HttpResult<Boolean> update(@Validated @RequestBody DictDO dictDO) {
        if (Objects.isNull(dictDO.getId())) {
            throw new BusinessException("当前字典ID不存在");
        }

        if (StrUtil.isBlank(dictDO.getName())) {
            throw new BusinessException("字典名称不能为空");
        }


        if (StrUtil.isBlank(dictDO.getDictType())) {
            throw new BusinessException("字典类型不能为空");
        }

        return HttpResult.ok(dictService.saveOrUpdate(dictDO));
    }

    /**
     * 移除
     *
     * @param id id
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @GetMapping("remove/{id}")
    @ApiOperation(value = "根据ID移除字典")
    public HttpResult<Boolean> remove(@PathVariable Integer id) {
        return HttpResult.ok(dictService.removeById(id));
    }

    /**
     * 列表
     *
     * @param field field
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @GetMapping("list/{field}")
    @ApiOperation(value = "根据字段获取字典列表")
    public HttpResult<List<DictDO>> list(@PathVariable String field) {
        if (StrUtil.isBlank(field)) {
            throw new BusinessException("当前字典类型字段不存在");
        }

        List<DictDO> dictDOList = dictService.list(Wrappers.<DictDO>lambdaQuery().eq(DictDO::getDictType, field));
        return HttpResult.ok(dictDOList);
    }
}
