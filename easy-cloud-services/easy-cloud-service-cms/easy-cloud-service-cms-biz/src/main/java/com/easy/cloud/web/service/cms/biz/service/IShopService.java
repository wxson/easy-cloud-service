package com.easy.cloud.web.service.cms.biz.service;

import com.easy.cloud.web.service.cms.biz.domain.vo.ShopVO;

import java.util.List;

/**
 * @author GR
 */
public interface IShopService {
    /**
     * 获取商城列表
     *
     * @return java.util.List<com.easy.cloud.web.service.cms.biz.domain.vo.ShopVO>
     */
    List<ShopVO> list();
}
