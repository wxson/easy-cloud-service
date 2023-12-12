package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.service.member.api.dto.MemberBalanceDTO;
import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Member API
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Slf4j
@RestController
@RequestMapping(value = "member")
public class MemberController {

  @Autowired
  private IMemberService memberService;

  /**
   * 新增
   *
   * @param memberDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  public HttpResult<MemberVO> save(@Validated @RequestBody MemberDTO memberDTO) {
    return HttpResult.ok(memberService.save(memberDTO));
  }

  /**
   * 更新
   *
   * @param memberDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  public HttpResult<MemberVO> update(@Validated @RequestBody MemberDTO memberDTO) {
    return HttpResult.ok(memberService.update(memberDTO));
  }


  /**
   * 更新
   *
   * @param memberDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update/profile")
  public HttpResult<MemberVO> updateProfile(@Validated @RequestBody MemberDTO memberDTO) {
    return HttpResult.ok(memberService.updateProfile(memberDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param memberId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{memberId}")
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
  public HttpResult<MemberVO> detailById(
      @PathVariable @NotNull(message = "当前ID不能为空") String memberId) {
    return HttpResult.ok(memberService.detailById(memberId));
  }

  /**
   * 根据用户ID获取会员详情
   *
   * @param userId 用户ID
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
   */
  @Inner
  @GetMapping("detail/{userId}")
  @ApiOperation(value = "根据用户ID获取会员详情")
  public HttpResult<MemberVO> getMemberDetailByUserId(
      @PathVariable @ApiParam("用户ID") String userId) {
    return HttpResult.ok(memberService.getMemberDetailByUserId(userId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
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
  public HttpResult<Page<MemberVO>> page(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(memberService.page(page, size));
  }


  /**
   * 修改会员信息
   *
   * @param memberDTO 会员信息
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
   */
  @Inner
  @PostMapping("init")
  @ApiOperation(value = "初始化会员信息")
  public HttpResult<MemberVO> initMemberInfo(@RequestBody MemberDTO memberDTO) {
    return HttpResult.ok(memberService.initMemberInfo(memberDTO));
  }

  /**
   * 修改会员资产
   *
   * @param memberBalanceDTO 会员资产变动信息
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
   */
  @PostMapping("balance/update")
  @ApiOperation(value = "更新会员资产信息")
  public HttpResult<MemberVO> updateMemberProperty(@RequestBody MemberBalanceDTO memberBalanceDTO) {
    return HttpResult.ok(memberService.updateMemberProperty(memberBalanceDTO));
  }
}