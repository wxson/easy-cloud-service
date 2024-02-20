package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.vo.MemberPointsRecordVO;
import com.easy.cloud.web.service.member.biz.service.IMemberPointsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MemberPointsRecord API
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
@Slf4j
@RestController
@RequestMapping(value = "member/points/record")
@Api(value = "MemberPointsRecord", tags = "会员积分记录管理")
public class MemberPointsRecordController {

    @Autowired
    private IMemberPointsRecordService memberPointsRecordService;

    /**
     * 根据ID获取详情
     *
     * @param memberPointsRecordId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{memberPointsRecordId}")
    @ApiOperation(value = "根据ID获取会员积分记录详情")
    public HttpResult<MemberPointsRecordVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String memberPointsRecordId) {
        return HttpResult.ok(memberPointsRecordService.detailById(memberPointsRecordId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取会员积分记录列表")
    public HttpResult<List<MemberPointsRecordVO>> list(@RequestParam @NotBlank(message = "用户ID不能为空") String userId) {
        return HttpResult.ok(memberPointsRecordService.list(userId));
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取会员积分记录分页列表")
    public HttpResult<Page<MemberPointsRecordVO>> page(@RequestParam @NotBlank(message = "用户ID不能为空") String userId,
                                                       @RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(memberPointsRecordService.page(userId, page, size));
    }
}