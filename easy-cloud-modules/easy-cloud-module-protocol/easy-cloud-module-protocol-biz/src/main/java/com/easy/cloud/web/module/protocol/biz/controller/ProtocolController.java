package com.easy.cloud.web.module.protocol.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.module.protocol.api.dto.ProtocolDTO;
import com.easy.cloud.web.module.protocol.api.vo.ProtocolVO;
import com.easy.cloud.web.module.protocol.biz.service.IProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Protocol API
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
@Slf4j
@RestController
@RequestMapping(value = "protocol")
@Api(value = "Protocol", tags = "协议管理")
public class ProtocolController {

    @Autowired
    private IProtocolService protocolService;

    /**
     * 新增
     *
     * @param protocolDTO 新增数据
     * @return 新增数据
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "新增信息接口")
    public HttpResult<ProtocolVO> save(@Validated @RequestBody ProtocolDTO protocolDTO) {
        return HttpResult.ok(protocolService.save(protocolDTO));
    }

    /**
     * 更新
     *
     * @param protocolDTO 新增数据
     * @return 更新数据
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "修改信息接口")
    public HttpResult<ProtocolVO> update(@Validated @RequestBody ProtocolDTO protocolDTO) {
        return HttpResult.ok(protocolService.update(protocolDTO));
    }

    /**
     * 根据ID移除数据
     *
     * @param protocolId ID
     * @return 是否删除成功
     */
    @GetMapping(value = "remove/{protocolId}")
    @ApiOperation(value = "根据ID删除信息接口")
    public HttpResult<Boolean> removeById(@PathVariable @NotBlank(message = "当前ID不能为空") String protocolId) {
        return HttpResult.ok(protocolService.removeById(protocolId));
    }

    /**
     * 根据ID获取详情
     *
     * @param protocolId ID
     * @return 详情数据
     */
    @Inner
    @GetMapping(value = "detail/{protocolId}")
    @ApiOperation(value = "根据ID获取信息详情")
    public HttpResult<ProtocolVO> detailById(@PathVariable @NotBlank(message = "当前ID不能为空") String protocolId) {
        return HttpResult.ok(protocolService.detailById(protocolId));
    }

    /**
     * 根据ID获取详情
     *
     * @param uniqueId 协议唯一标识
     * @return 详情数据
     */
    @Inner
    @GetMapping(value = "app/detail/{uniqueId}")
    @ApiOperation(value = "根据协议唯一标识获取信息详情")
    public HttpResult<ProtocolVO> appDetailByUniqueId(@PathVariable @NotBlank(message = "当前协议唯一标识不能为空") String uniqueId) {
        return HttpResult.ok(protocolService.appDetailByUniqueId(uniqueId));
    }

    /**
     * TODO 所有数据列表，查询参数自定义
     *
     * @return 查询列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "获取信息列表接口")
    public HttpResult<List<ProtocolVO>> list() {
        return HttpResult.ok(protocolService.list());
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param page 当前页
     * @param size 每页大小
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "获取信息分页接口")
    public HttpResult<Page<ProtocolVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return HttpResult.ok(protocolService.page(page, size));
    }
}