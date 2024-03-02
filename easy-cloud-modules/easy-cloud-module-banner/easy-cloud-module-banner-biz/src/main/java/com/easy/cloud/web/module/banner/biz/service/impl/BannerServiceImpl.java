package com.easy.cloud.web.module.banner.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.mysql.query.SpecificationWrapper;
import com.easy.cloud.web.module.banner.api.dto.BannerDTO;
import com.easy.cloud.web.module.banner.api.dto.BannerQueryDTO;
import com.easy.cloud.web.module.banner.api.vo.BannerVO;
import com.easy.cloud.web.module.banner.biz.converter.BannerConverter;
import com.easy.cloud.web.module.banner.biz.domain.BannerDO;
import com.easy.cloud.web.module.banner.biz.repository.BannerRepository;
import com.easy.cloud.web.module.banner.biz.service.IBannerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Banner 业务逻辑
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Slf4j
@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BannerVO save(BannerDTO bannerDTO) {
        // 转换成DO对象
        BannerDO banner = BannerConverter.convertTo(bannerDTO);
        // TODO 校验逻辑

        // 存储
        bannerRepository.save(banner);
        // 转换对象
        return BannerConverter.convertTo(banner);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BannerVO update(BannerDTO bannerDTO) {
        // 转换成DO对象
        if (Objects.isNull(bannerDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验
        BannerDO banner = bannerRepository.findById(bannerDTO.getId())
                .orElseThrow(() -> new BusinessException("当前菜单信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(bannerDTO, banner, true);
        // 更新
        bannerRepository.save(banner);
        // 转换对象
        return BannerConverter.convertTo(banner);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String bannerId) {
        // TODO 业务逻辑校验

        // 删除
        bannerRepository.deleteById(bannerId);
        return true;
    }

    @Override
    public BannerVO detailById(String bannerId) {
        // TODO 业务逻辑校验

        // 删除
        BannerDO banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return BannerConverter.convertTo(banner);
    }

    @Override
    public List<BannerVO> list(BannerQueryDTO bannerQuery) {
        // 获取列表数据
        List<BannerDO> banners = bannerRepository.findAll(SpecificationWrapper.query()
                .like(StringUtils.isNoneBlank(bannerQuery.getTitle()), BannerDO::getTitle, bannerQuery.getTitle())
                .eq(Objects.nonNull(bannerQuery.getType()), BannerDO::getType, bannerQuery.getType())
                .eq(Objects.nonNull(bannerQuery.getDevice()), BannerDO::getDevice, bannerQuery.getDevice())
        );
        return BannerConverter.convertTo(banners);
    }

    @Override
    public Page<BannerVO> page(BannerQueryDTO bannerQuery) {
        bannerQuery.setPage(Objects.nonNull(bannerQuery.getPage()) ? bannerQuery.getPage() : 1);
        bannerQuery.setSize(Objects.nonNull(bannerQuery.getSize()) ? bannerQuery.getSize() : 10);
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, bannerQuery.getPage() - 1), bannerQuery.getSize());
        return BannerConverter.convertTo(bannerRepository.findAll(pageable));
    }
}