package com.easy.cloud.web.service.cms.biz.controller;

import com.easy.cloud.web.service.cms.biz.domain.db.VipGoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.VipGoodsDTO;
import com.easy.cloud.web.service.cms.biz.domain.query.VipGoodsQuery;
import com.easy.cloud.web.service.cms.biz.service.IVipGoodsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签到
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("vip/goods")
@AllArgsConstructor
@Api(value = "Vip商品管理", tags = "Vip商品管理")
public class VipGoodsController extends BaseController<VipGoodsQuery, VipGoodsDTO, VipGoodsDO> {

    private final IVipGoodsService vipGoodsService;

    @Override
    public IRepositoryService<VipGoodsDO> getService() {
        return vipGoodsService;
    }
}
