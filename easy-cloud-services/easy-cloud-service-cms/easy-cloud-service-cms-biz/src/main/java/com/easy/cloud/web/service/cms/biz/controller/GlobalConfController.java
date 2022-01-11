package com.easy.cloud.web.service.cms.biz.controller;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.biz.enums.GlobalConfEnum;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 商品
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("global/conf")
@AllArgsConstructor
@Api(value = "全局配置管理", tags = "全局配置管理")
public class GlobalConfController {

    private final IGlobalConfService globalConfService;

    /**
     * 根据商品编码获取商品详情
     *
     * @param globalConfCode 全局配置code
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.String>
     */
    @GetMapping("value/{globalConfCode}")
    @ApiOperation(value = "根据商品编码获取商品信息")
    public HttpResult<String> getGlobalConfValueByKey(@PathVariable @ApiParam("全局配置Code") Integer globalConfCode) {
        Optional<GlobalConfEnum> optionalGlobalConfEnum = GlobalConfEnum.getInstanceByCode(globalConfCode);
        if (!optionalGlobalConfEnum.isPresent()) {
            throw new BusinessException("当前全局配置code有误");
        }
        return HttpResult.ok(globalConfService.getGlobalConfValueByKey(optionalGlobalConfEnum.get().getKey()));
    }
}
