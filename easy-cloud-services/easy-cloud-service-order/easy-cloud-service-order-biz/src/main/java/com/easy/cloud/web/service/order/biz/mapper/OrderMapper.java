package com.easy.cloud.web.service.order.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.cloud.web.service.order.biz.domain.db.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GR
 * @date 2021-11-12 18:36
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
