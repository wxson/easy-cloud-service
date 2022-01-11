package com.easy.cloud.web.service.charts.biz.service.impl;

import com.easy.cloud.web.module.online.service.IOnlineService;
import com.easy.cloud.web.service.charts.biz.domain.vo.ChartsVO;
import com.easy.cloud.web.service.charts.biz.domain.vo.PanelVO;
import com.easy.cloud.web.service.charts.biz.enums.PanelEnum;
import com.easy.cloud.web.service.charts.biz.service.IChartsService;
import com.easy.cloud.web.service.cms.api.feign.CmsFeignClientService;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GR
 * @date 2021-12-26 15:45
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChartsServiceImpl implements IChartsService {

    private final IOnlineService onlineService;

    private final UpmsFeignClientService upmsFeignClientService;

    private final OrderFeignClientService orderFeignClientService;

    private final CmsFeignClientService cmsFeignClientService;

    @Override
    public ChartsVO statisticsPanelData() {
        List<PanelVO> panelList = new ArrayList<>();
        // 在线数据
        Integer onlineNumber = onlineService.getOnlineNumber();
        PanelVO onlinePanel = PanelVO.build().setType(PanelEnum.ONLINE).setNum(onlineNumber);
        panelList.add(onlinePanel);
        // 平台收益
        PanelVO earningsPanel = PanelVO.build().setType(PanelEnum.EARNINGS).setNum(168);
        panelList.add(earningsPanel);
        // 注册人数
        PanelVO membersPanel = PanelVO.build().setType(PanelEnum.MEMBERS).setNum(89);
        panelList.add(membersPanel);
        // 商品登记
        PanelVO goodsPanel = PanelVO.build().setType(PanelEnum.GOODS).setNum(1299);
        panelList.add(goodsPanel);
        return ChartsVO.build().setPanelList(panelList);
    }

    @Override
    public ChartsVO statisticsRecentWeekData() {
        return ChartsVO.build();
    }

    @Override
    public ChartsVO statisticsHotGoodsData() {
        return ChartsVO.build();
    }

    @Override
    public ChartsVO statisticsPotentialUserData() {
        return ChartsVO.build();
    }
}
