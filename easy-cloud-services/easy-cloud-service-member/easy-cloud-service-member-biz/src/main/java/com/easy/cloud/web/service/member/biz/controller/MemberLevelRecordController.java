package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.vo.MemberLevelRecordVO;
import com.easy.cloud.web.service.member.biz.service.IMemberLevelRecordService;
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
 * MemberLevelRecord API
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
@Slf4j
@RestController
@RequestMapping(value = "member/level/record")
@Api(value = "MemberLevelRecord", tags = "会员等级记录管理")
public class MemberLevelRecordController {

    @Autowired
    private IMemberLevelRecordService memberLevelRecordService;

    /**
     * 根据ID获取详情
     *
     * @param memberLevelRecordId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{memberLevelRecordId}")
    @ApiOperation(value = "根据ID获取会员等级记录详情")
    public HttpResult<MemberLevelRecordVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String memberLevelRecordId) {
        return HttpResult.ok(memberLevelRecordService.detailById(memberLevelRecordId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @param userId 查询记录的用户ID
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取会员等级记录列表")
    public HttpResult<List<MemberLevelRecordVO>> list(@RequestParam @NotBlank(message = "用户ID不能为空") String userId) {
        return HttpResult.ok(memberLevelRecordService.list(userId));
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param userId 查询记录的用户ID
     * @param page   当前页
     * @param size   每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取会员等级记录分页列表")
    public HttpResult<Page<MemberLevelRecordVO>> page(@RequestParam @NotBlank(message = "用户ID不能为空") String userId,
                                                      @RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(memberLevelRecordService.page(userId, page, size));
    }
}