package com.easy.cloud.web.service.cms.biz.controller;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.enums.ActionEnum;
import com.easy.cloud.web.service.cms.biz.enums.GlobalConfEnum;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 活动管理
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("action")
@AllArgsConstructor
@Api(value = "活动相关管理", tags = "活动相关管理")
public class ActionController {

    private final IGlobalConfService globalConfService;

    /**
     * 执行对应的活动，一般为领取当前活动的奖励
     * 与支付的区别在于，支付类似于一种解锁的操作，具体的商品领取，执行handle方法
     *
     * @param actionDTO 活动信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Object>
     */
    @PostMapping("handle")
    @ApiOperation(value = "执行活动相关操作（付款除外）")
    public HttpResult<Object> handle(@Validated @RequestBody ActionDTO actionDTO) {
        Optional<IActionService> actionServiceOptional = actionDTO.getAction().getActionService();
        if (!actionServiceOptional.isPresent()) {
            throw new BusinessException("系统错误，请联系管理员");
        }

        // 固定类型绑定固定商品
        // 绑定电话
        if (ActionEnum.BIND_PHONE == actionDTO.getAction()) {
            String bindTelGoodsNo = globalConfService.getGlobalConfValueByKey(GlobalConfEnum.BIND_TEL_GOODS_NO.getKey());
            actionDTO.setGoodsNo(bindTelGoodsNo);
        }

        // 实名认证
        if (ActionEnum.CERTIFICATION == actionDTO.getAction()) {
            String certificationGoodsNo = globalConfService.getGlobalConfValueByKey(GlobalConfEnum.CERTIFICATION_GOODS_NO.getKey());
            actionDTO.setGoodsNo(certificationGoodsNo);
        }

        return HttpResult.ok(actionServiceOptional.get().handle(actionDTO));
    }

    /**
     * 获取活动内容
     *
     * @param actionDTO 活动
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Object>
     */
    @PostMapping("list")
    @ApiOperation(value = "获取每一个活动的相关商品列表")
    public HttpResult<Object> list(@Validated @RequestBody ActionDTO actionDTO) {
        Optional<IActionService> actionServiceOptional = actionDTO.getAction().getActionService();
        if (!actionServiceOptional.isPresent()) {
            throw new BusinessException("系统错误，请联系管理员");
        }

        return HttpResult.ok(actionServiceOptional.get().list(actionDTO));
    }
}
