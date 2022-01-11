package com.easy.cloud.web.service.cms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.biz.domain.vo.ShopVO;
import com.easy.cloud.web.service.cms.biz.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 签到
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("shop")
@AllArgsConstructor
@Api(value = "商城管理", tags = "商城管理")
public class ShopController {

    private final IShopService shopService;

    /**
     * 获取商品列表
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.boolean>
     */
    @PostMapping("list")
    @ApiOperation(value = "获取商品列表")
    public HttpResult<List<ShopVO>> list() {
        return HttpResult.ok(shopService.list());
    }
}
