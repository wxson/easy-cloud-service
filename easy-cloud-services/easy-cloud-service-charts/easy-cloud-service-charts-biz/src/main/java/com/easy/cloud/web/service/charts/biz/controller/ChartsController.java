package com.easy.cloud.web.service.charts.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO;
import com.easy.cloud.web.service.charts.biz.service.IChartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GR
 * @date 2021-12-26 15:42
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "数据统计相关管理", tags = "数据统计相关管理")
public class ChartsController {

    private final IChartsService chartsService;

    /**
     * 数据统计
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO>
     */
    @GetMapping(value = "statistics/panel")
    @ApiOperation(value = "统计面板：在线人数、平台收益、注册人数、商品等级")
    public HttpResult<ChartsVO> statisticsPanelData() {
        return HttpResult.ok(chartsService.statisticsPanelData());
    }


    /**
     * 统计最近一周的数据
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO>
     */
    @GetMapping(value = "statistics/recent/week")
    @ApiOperation(value = "统计最近七天的数据")
    public HttpResult<ChartsVO> statisticsRecentWeekData() {
        return HttpResult.ok(chartsService.statisticsRecentWeekData());
    }

    /**
     * 统计热销商品
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO>
     */
    @GetMapping(value = "statistics/hot/goods")
    @ApiOperation(value = "统计热销商品")
    public HttpResult<ChartsVO> statisticsHotGoodsData() {
        return HttpResult.ok(chartsService.statisticsHotGoodsData());
    }

    /**
     * 统计潜力用户
     *
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO>
     */
    @GetMapping(value = "statistics/potential/user")
    @ApiOperation(value = "统计潜力用户")
    public HttpResult<ChartsVO> statisticsPotentialUserData() {
        return HttpResult.ok(chartsService.statisticsPotentialUserData());
    }
}
