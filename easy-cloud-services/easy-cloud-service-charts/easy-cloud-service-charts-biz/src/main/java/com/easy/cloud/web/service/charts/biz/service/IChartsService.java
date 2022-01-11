package com.easy.cloud.web.service.charts.biz.service;

import com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO;

/**
 * @author GR
 * @date 2021-12-26 15:44
 */
public interface IChartsService {
    /**
     * 总计面板数据
     *
     * @return com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO
     */
    ChartsVO statisticsPanelData();


    /**
     * 总计最近一周数据
     *
     * @return com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO
     */
    ChartsVO statisticsRecentWeekData();

    /**
     * 总计热销商品
     *
     * @return com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO
     */
    ChartsVO statisticsHotGoodsData();

    /**
     * 总计潜力用户
     *
     * @return com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO
     */
    ChartsVO statisticsPotentialUserData();
}
