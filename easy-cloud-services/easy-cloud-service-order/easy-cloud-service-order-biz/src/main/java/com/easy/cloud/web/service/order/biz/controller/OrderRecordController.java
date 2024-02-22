package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.dto.OrderRecordQueryDTO;
import com.easy.cloud.web.service.order.api.vo.OrderRecordVO;
import com.easy.cloud.web.service.order.biz.service.IOrderRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * OrderRecord API
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Slf4j
@RestController
@RequestMapping(value = "record")
@Tag(name = "订单操作记录管理", description = "订单操作记录管理")
public class OrderRecordController {

    @Autowired
    private IOrderRecordService orderRecordService;

    /**
     * 新增
     *
     * @param orderRecordDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "save")
    public HttpResult<OrderRecordVO> save(@Validated @RequestBody OrderRecordDTO orderRecordDTO) {
        return HttpResult.ok(orderRecordService.save(orderRecordDTO));
    }

    /**
     * 根据ID获取详情
     *
     * @param id ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{id}")
    public HttpResult<OrderRecordVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String id) {
        return HttpResult.ok(orderRecordService.detailById(id));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    public HttpResult<List<OrderRecordVO>> list(OrderRecordQueryDTO orderRecordQueryDTO) {
        return HttpResult.ok(orderRecordService.list(orderRecordQueryDTO));
    }
}