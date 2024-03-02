package com.easy.cloud.web.module.banner.biz.service;

import com.easy.cloud.web.module.banner.api.dto.BannerDTO;
import com.easy.cloud.web.module.banner.api.dto.BannerQueryDTO;
import com.easy.cloud.web.module.banner.api.vo.BannerVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Banner interface
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
public interface IBannerService {

    /**
     * 新增数据
     *
     * @param bannerDTO 保存参数
     * @return com.easy.cloud.web.module.banner.api.vo.BannerVO
     */
    BannerVO save(BannerDTO bannerDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param bannerDTO 保存参数
     * @return com.easy.cloud.web.module.banner.api.vo.BannerVO
     */
    BannerVO update(BannerDTO bannerDTO);

    /**
     * 根据ID删除数据
     *
     * @param bannerId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String bannerId);

    /**
     * 根据ID获取详情
     *
     * @param bannerId 对象ID
     * @return java.lang.Boolean
     */
    BannerVO detailById(String bannerId);

    /**
     * 根据条件获取列表数据
     *
     * @param bannerQuery 查询参数
     * @return List<com.easy.cloud.web.module.banner.api.vo.BannerVO> 返回列表数据
     */
    List<BannerVO> list(BannerQueryDTO bannerQuery);

    /**
     * 根据条件获取分页数据
     *
     * @param bannerQuery 查询参数
     * @return List<com.easy.cloud.web.module.banner.api.vo.BannerVO> 返回列表数据
     */
    Page<BannerVO> page(BannerQueryDTO bannerQuery);
}