package com.easy.cloud.web.service.charts.biz.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author GR
 * @date 2021-12-26 16:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class ChartsVO {
    /**
     * 面板数组
     */
    private List<PanelVO> panelList;
    /**
     * 最近一周数据
     */
    private List<RecentWeekVO> recentWeekList;
    /**
     * 畅销商品
     */
    private List<HotGoodsVO> hotGoodsList;
    /**
     * 潜力用户
     */
    private List<PotentialUserVO> potentialUserList;
}
