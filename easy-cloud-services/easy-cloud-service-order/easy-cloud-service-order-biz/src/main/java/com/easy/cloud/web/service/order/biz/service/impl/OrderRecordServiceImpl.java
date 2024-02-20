package com.easy.cloud.web.service.order.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.mysql.query.SpecificationWrapper;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.dto.OrderRecordQueryDTO;
import com.easy.cloud.web.service.order.api.vo.OrderRecordVO;
import com.easy.cloud.web.service.order.biz.converter.OrderRecordConverter;
import com.easy.cloud.web.service.order.biz.domain.OrderRecordDO;
import com.easy.cloud.web.service.order.biz.repository.OrderRecordRepository;
import com.easy.cloud.web.service.order.biz.service.IOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * OrderRecord 业务逻辑
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Slf4j
@Service
public class OrderRecordServiceImpl implements IOrderRecordService {

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderRecordVO save(OrderRecordDTO orderRecordDTO) {
        // 转换成DO对象
        OrderRecordDO orderRecord = OrderRecordConverter.convertTo(orderRecordDTO);
        // TODO 校验逻辑

        // 设置当前订单用户名称
        orderRecord.setOperator(SecurityUtils.getAuthenticationUser().getUsername());
        // 存储
        orderRecordRepository.save(orderRecord);
        // 转换对象
        return OrderRecordConverter.convertTo(orderRecord);
    }

    @Override
    public OrderRecordVO detailById(String orderRecordId) {
        // TODO 业务逻辑校验

        // 删除
        OrderRecordDO orderRecord = orderRecordRepository.findById(orderRecordId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return OrderRecordConverter.convertTo(orderRecord);
    }

    @Override
    public List<OrderRecordVO> list(OrderRecordQueryDTO orderRecordQueryDTO) {
        // 获取列表数据
        List<OrderRecordDO> orderRecords = orderRecordRepository.findAll(SpecificationWrapper
                // orderNo eq
                .where(StrUtil.isNotBlank(orderRecordQueryDTO.getOrderNo()), OrderRecordDO::getOrderNo, orderRecordQueryDTO.getOrderNo())
                // 时间降序
                .orderDesc(OrderRecordDO::getCreateAt)
        );
        return OrderRecordConverter.convertTo(orderRecords);
    }
}