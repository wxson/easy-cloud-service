package com.easy.cloud.web.service.member.biz.controller;

import com.easy.cloud.web.service.member.biz.domain.db.VipRechargeDO;
import com.easy.cloud.web.service.member.biz.domain.dto.VipRechargeDTO;
import com.easy.cloud.web.service.member.biz.domain.query.VipRechargeQuery;
import com.easy.cloud.web.service.member.biz.service.IVipRechargeService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GR
 * @date 2021-11-28 18:02
 */
@Slf4j
@RestController
@RequestMapping("recharge")
@AllArgsConstructor
@Api(value = "会员等级充值管理", tags = "会员等级充值管理")
public class VipRechargeController extends BaseController<VipRechargeQuery, VipRechargeDTO, VipRechargeDO> {

    private final IVipRechargeService vipRechargeService;

    @Override
    public IRepositoryService<VipRechargeDO> getService() {
        return vipRechargeService;
    }
}
