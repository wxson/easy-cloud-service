package com.easy.cloud.web.module.banner.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.banner.api.dto.BannerDTO;
import com.easy.cloud.web.module.banner.api.vo.BannerVO;
import com.easy.cloud.web.module.banner.biz.domain.BannerDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Banner转换器
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
public class BannerConverter {

    /**
     * DTO转为DO
     *
     * @param banner 转换数据
     * @return com.easy.cloud.web.module.banner.biz.domain.BannerDO
     */
    public static BannerDO convertTo(BannerDTO banner) {
        BannerDO bannerDO = BannerDO.builder().build();
        BeanUtils.copyProperties(banner, bannerDO, true);
        return bannerDO;
    }

    /**
     * DO转为VO
     *
     * @param banner 转换数据
     * @return com.easy.cloud.web.module.banner.api.vo.BannerVO
     */
    public static BannerVO convertTo(BannerDO banner) {
        BannerVO bannerVO = BannerVO.builder().build();
        BeanUtils.copyProperties(banner, bannerVO, true);
        return bannerVO;
    }

    /**
     * 列表DO转为VO
     *
     * @param banners 转换数据
     * @return com.easy.cloud.web.module.banner.api.vo.BannerVO
     */
    public static List<BannerVO> convertTo(List<BannerDO> banners) {
        return banners.stream()
                .map(BannerConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.module.banner.api.vo.BannerVO
     */
    public static Page<BannerVO> convertTo(Page<BannerDO> page) {
        return page.map(BannerConverter::convertTo);
    }
}