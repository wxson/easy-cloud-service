package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.dto.InvoiceDTO;
import com.easy.cloud.web.service.order.biz.service.IInvoiceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * Invoice API
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
@Slf4j
@RestController
@RequestMapping(value = "invoice")
@Tag(name = "Invoice", description = "发票抬头管理")
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    /**
     * 新增
     *
     * @param invoiceDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "创建发票抬头")
    public Object save(@Validated @RequestBody InvoiceDTO invoiceDTO) {
        return HttpResult.ok(invoiceService.save(invoiceDTO));
    }

    /**
     * 更新
     *
     * @param invoiceDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新发票抬头")
    public Object update(@Validated @RequestBody InvoiceDTO invoiceDTO) {
        return HttpResult.ok(invoiceService.update(invoiceDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param invoiceId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{invoiceId}")
    @ApiOperation(value = "删除发票抬头")
    public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") String invoiceId) {
        return HttpResult.ok(invoiceService.removeById(invoiceId));
    }

    /**
     * 根据ID获取详情
     *
     * @param invoiceId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{invoiceId}")
    @ApiOperation(value = "发票抬头详情")
    public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") String invoiceId) {
        return HttpResult.ok(invoiceService.detailById(invoiceId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "发票抬头列表")
    public Object list() {
        return HttpResult.ok(invoiceService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "发票抬头分页")
    public Object page(@RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(invoiceService.page(page, size));
    }
}