package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.dto.MemberLevelDTO;
import com.easy.cloud.web.service.member.api.vo.MemberLevelVO;
import com.easy.cloud.web.service.member.biz.service.IMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MemberLevel API
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
@Slf4j
@RestController
@RequestMapping(value = "member/level")
@Api(value = "MemberLevel", tags = "会员等级管理")
public class MemberLevelController {

    @Autowired
    private IMemberLevelService memberLevelService;

    /**
     * 新增
     *
     * @param memberLevelDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "新增会员等级信息")
    public HttpResult<MemberLevelVO> save(@Validated @RequestBody MemberLevelDTO memberLevelDTO) {
        return HttpResult.ok(memberLevelService.save(memberLevelDTO));
    }

    /**
     * 更新
     *
     * @param memberLevelDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新会员等级信息")
    public HttpResult<MemberLevelVO> update(@Validated @RequestBody MemberLevelDTO memberLevelDTO) {
        return HttpResult.ok(memberLevelService.update(memberLevelDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param memberLevelId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{memberLevelId}")
    @ApiOperation(value = "根据ID移除会员等级信息")
    public HttpResult<Boolean> removeById(@PathVariable @NotNull(message = "当前ID不能为空") String memberLevelId) {
        return HttpResult.ok(memberLevelService.removeById(memberLevelId));
    }

    /**
     * 根据ID获取详情
     *
     * @param memberLevelId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{memberLevelId}")
    @ApiOperation(value = "根据ID获取会员等级信息")
    public HttpResult<MemberLevelVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String memberLevelId) {
        return HttpResult.ok(memberLevelService.detailById(memberLevelId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取会员等级信息列表")
    public HttpResult<List<MemberLevelVO>> list() {
        return HttpResult.ok(memberLevelService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取会员等级信息分页列表")
    public HttpResult<Page<MemberLevelVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(memberLevelService.page(page, size));
    }
}