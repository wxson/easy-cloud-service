package com.easy.cloud.web.service.member.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.biz.domain.db.MemberStorageDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberStorageDTO;
import com.easy.cloud.web.service.member.biz.domain.query.MemberStorageQuery;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberStorageVO;
import com.easy.cloud.web.service.member.biz.service.IMemberStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author GR
 * @date 2021-11-28 18:02
 */
@Slf4j
@RestController
@RequestMapping("member/storage")
@AllArgsConstructor
@Api(value = "会员仓库管理", tags = "会员仓库管理")
public class MemberStorageController extends BaseController<MemberStorageQuery, MemberStorageDTO, MemberStorageDO> {

    private final IMemberStorageService memberStorageService;

    @Override
    public IRepositoryService<MemberStorageDO> getService() {
        return memberStorageService;
    }

    /**
     * 仓库存储金币
     *
     * @param memberStorageDTO 仓库数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("save/amount")
    @ApiOperation(value = "仓库存储金币")
    public HttpResult<Boolean> saveAmount(@RequestBody MemberStorageDTO memberStorageDTO) {
        return HttpResult.ok(memberStorageService.saveAmount(memberStorageDTO));
    }

    /**
     * 取出仓库存储金币
     *
     * @param memberStorageDTO 仓库数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("out/amount")
    @ApiOperation(value = "仓库存储金币")
    public HttpResult<Boolean> takeOutAmount(@RequestBody MemberStorageDTO memberStorageDTO) {
        return HttpResult.ok(memberStorageService.takeOutAmount(memberStorageDTO));
    }

    /**
     * 获取仓库存储金币
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @GetMapping("detail")
    @ApiOperation(value = "仓库存储金币")
    public HttpResult<MemberStorageVO> storageDetail() {
        MemberStorageDO memberStorageDO = memberStorageService.getOne(Wrappers.<MemberStorageDO>lambdaQuery().eq(MemberStorageDO::getUserId, SecurityUtils.getAuthenticationUser().getId()));
        if (Objects.isNull(memberStorageDO)) {
            memberStorageDO = memberStorageService.initMemberStorage(SecurityUtils.getAuthenticationUser().getId());
        }

        return HttpResult.ok(memberStorageDO.convert());
    }

    /**
     * 设置仓库使用权限
     *
     * @param status 仓库状态
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("enable/permission/{status}")
    @ApiOperation(value = "设置仓库使用权限")
    public HttpResult<Boolean> enableStoragePermission(@PathVariable Integer status) {
        // 修改当前会员的仓库使用权限
        memberStorageService.update(Wrappers.<MemberStorageDO>lambdaUpdate()
                .eq(MemberStorageDO::getUserId, SecurityUtils.getAuthenticationUser().getId())
                .set(MemberStorageDO::getStatus, status));
        return HttpResult.ok(true);
    }
}
