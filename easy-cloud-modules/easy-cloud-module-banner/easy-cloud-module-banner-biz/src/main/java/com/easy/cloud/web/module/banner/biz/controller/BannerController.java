package com.easy.cloud.web.module.banner.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.banner.api.dto.BannerDTO;
import com.easy.cloud.web.module.banner.api.dto.BannerQueryDTO;
import com.easy.cloud.web.module.banner.api.vo.BannerVO;
import com.easy.cloud.web.module.banner.biz.service.IBannerService;
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
 * Banner API
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Slf4j
@RestController
@RequestMapping(value = "banner")
@Api(value = "Banner", tags = "广告管理")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    /**
     * 新增
     *
     * @param bannerDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "广告新增")
    public HttpResult<BannerVO> save(@Validated @RequestBody BannerDTO bannerDTO) {
        return HttpResult.ok(bannerService.save(bannerDTO));
    }

    /**
     * 更新
     *
     * @param bannerDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "广告修改")
    public HttpResult<BannerVO> update(@Validated @RequestBody BannerDTO bannerDTO) {
        return HttpResult.ok(bannerService.update(bannerDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param bannerId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{bannerId}")
    @ApiOperation(value = "根据广告ID移除广告")
    public HttpResult<Boolean> removeById(@PathVariable @NotNull(message = "当前ID不能为空") String bannerId) {
        return HttpResult.ok(bannerService.removeById(bannerId));
    }

    /**
     * 根据ID获取详情
     *
     * @param bannerId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{bannerId}")
    @ApiOperation(value = "根据广告ID获取广告详情")
    public HttpResult<BannerVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String bannerId) {
        return HttpResult.ok(bannerService.detailById(bannerId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "查询广告列表")
    public HttpResult<List<BannerVO>> list(BannerQueryDTO bannerQuery) {
        return HttpResult.ok(bannerService.list(bannerQuery));
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param bannerQuery 查询参数
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    public HttpResult<Page<BannerVO>> page(BannerQueryDTO bannerQuery) {
        return HttpResult.ok(bannerService.page(bannerQuery));
    }
}