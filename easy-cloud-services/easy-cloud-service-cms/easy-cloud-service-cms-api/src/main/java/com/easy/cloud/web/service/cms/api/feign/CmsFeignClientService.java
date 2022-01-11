package com.easy.cloud.web.service.cms.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.api.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.api.domain.dto.UserPlayRecordDTO;
import com.easy.cloud.web.service.cms.api.domain.vo.GoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.CMS_SERVICE)
public interface CmsFeignClientService {

    /**
     * 根据商品唯一编码获取商品详情
     *
     * @param no 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @GetMapping("goods/detail/{no}")
    HttpResult<GoodsVO> getGoodsDetailByNo(@PathVariable(value = "no") String no);


    /**
     * 插入对局记录
     *
     * @param userPlayRecordDTO 对局记录
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("player/insert/play/record")
    HttpResult<Boolean> insertPlayRecord(@RequestBody UserPlayRecordDTO userPlayRecordDTO);

    /**
     * action  handle
     *
     * @param actionDTO 对局记录
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("action/handle")
    HttpResult<Object> actionHandle(@RequestBody ActionDTO actionDTO);

    /**
     * 根据全局配置Code获取对应的值
     *
     * @param globalConfCode 全局配置code
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.String>
     */
    @GetMapping("value/{globalConfCode}")
    HttpResult<String> getGlobalConfValueByCode(@PathVariable(value = "globalConfCode") Integer globalConfCode);
}
