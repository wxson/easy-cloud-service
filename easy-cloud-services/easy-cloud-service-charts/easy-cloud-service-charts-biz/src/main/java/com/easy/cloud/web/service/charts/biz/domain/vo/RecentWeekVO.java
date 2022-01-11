package com.easy.cloud.web.service.charts.biz.domain.vo;

import com.easy.cloud.web.service.charts.biz.enums.PanelEnum;
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
public class RecentWeekVO {
    /**
     * 类型
     */
    private PanelEnum type;
    /**
     * 数量
     */
    private List<PanelVO> panelList;
}
