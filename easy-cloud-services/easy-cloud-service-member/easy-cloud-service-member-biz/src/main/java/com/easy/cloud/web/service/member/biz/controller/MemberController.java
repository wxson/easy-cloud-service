package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Member API
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Slf4j
@RestController
@RequestMapping(value = "member")
@Api(value = "Member", tags = "会员管理")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    /**
     * 更新
     *
     * @param memberDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新会员信息")
    public HttpResult<MemberVO> update(@Validated @RequestBody MemberDTO memberDTO) {
        return HttpResult.ok(memberService.update(memberDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param memberId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{memberId}")
    @ApiOperation(value = "根据会员ID移除会员信息")
    public HttpResult<Boolean> removeById(
            @PathVariable @NotNull(message = "当前ID不能为空") String memberId) {
        return HttpResult.ok(memberService.removeById(memberId));
    }

    /**
     * 根据ID获取详情
     *
     * @param memberId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{memberId}")
    @ApiOperation(value = "获取会员详情")
    public HttpResult<MemberVO> detailById(
            @PathVariable @NotNull(message = "当前ID不能为空") String memberId) {
        return HttpResult.ok(memberService.detailById(memberId));
    }

    /**
     * 根据用户ID获取会员详情
     *
     * @param userId 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.vo.MemberVO>
     */
    @Inner
    @GetMapping("detailByUserId/{userId}")
    @ApiOperation(value = "根据用户ID获取会员详情")
    public HttpResult<MemberVO> detailByUserId(
            @PathVariable @ApiParam("用户ID") String userId) {
        return HttpResult.ok(memberService.detailByUserId(userId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "会员列表")
    public HttpResult<List<MemberVO>> list() {
        return HttpResult.ok(memberService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "会员分页")
    public HttpResult<Page<MemberVO>> page(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(memberService.page(page, size));
    }


    /**
     * 修改会员信息
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.vo.MemberVO>
     */
    @Inner
    @PostMapping("create")
    @ApiOperation(value = "创建会员信息")
    public HttpResult<MemberVO> createMember() {
        return HttpResult.ok(memberService.createMember());
    }
}