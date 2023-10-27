package com.easy.cloud.web.service.cms.biz.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.GoodsDTO;
import com.easy.cloud.web.service.cms.biz.domain.query.GoodsQuery;
import com.easy.cloud.web.service.cms.biz.domain.vo.GoodsVO;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("goods")
@AllArgsConstructor
@Api(value = "商品管理", tags = "商品管理")
public class GoodsController extends BaseController<GoodsQuery, GoodsDTO, GoodsDO> {

    private final IGoodsService goodsService;

    @Override
    public IRepositoryService<GoodsDO> getService() {
        return goodsService;
    }

    /**
     * 根据商品编码获取商品详情
     *
     * @param no 商品编码
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.cms.biz.domain.vo.GoodsVO>
     */
    @GetMapping("detail/{no}")
    @ApiOperation(value = "根据商品编码获取商品信息")
    public HttpResult<GoodsVO> getGoodsDetailByNo(@PathVariable @ApiParam("商品编码") String no) {
        if (StrUtil.isBlank(no)) {
            throw new BusinessException("当前商品编码不能为空");
        }

        // 根据编码获取商品信息
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getNo, no));
        if (null == goodsDO) {
            throw new BusinessException("当前商品信息不存在");
        }

        return HttpResult.ok(goodsDO.convert());
    }
}
