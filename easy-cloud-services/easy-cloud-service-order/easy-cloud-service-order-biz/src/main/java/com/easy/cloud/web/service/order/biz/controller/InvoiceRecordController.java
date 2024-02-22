package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.vo.InvoiceRecordVO;
import com.easy.cloud.web.service.order.biz.service.IInvoiceRecordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * InvoiceRecord API
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
@Slf4j
@RestController
@RequestMapping(value = "invoice/record")
@Tag(name = "发票记录管理", description = "发票记录管理")
public class InvoiceRecordController {

    @Autowired
    private IInvoiceRecordService invoiceRecordService;


    /**
     * 根据ID获取详情
     *
     * @param invoiceRecordId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{invoiceRecordId}")
    @ApiOperation(value = "发票记录详情")
    public HttpResult<InvoiceRecordVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String invoiceRecordId) {
        return HttpResult.ok(invoiceRecordService.detailById(invoiceRecordId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "发票记录列表")
    public HttpResult<List<InvoiceRecordVO>> list() {
        return HttpResult.ok(invoiceRecordService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "发票记录分页")
    public HttpResult<Page<InvoiceRecordVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(invoiceRecordService.page(page, size));
    }
}