package com.easy.cloud.web.service.cms.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.mapper.DbGoodsMapper;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author GR
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<DbGoodsMapper, GoodsDO> implements IGoodsService {

    @Override
    public GoodsDO verifyBeforeSave(GoodsDO goodsDO) {
        // 初始化唯一编码
        this.createGoodsNo(goodsDO);
        // 根据no和name查询数据
        List<GoodsDO> goodsDOList = this.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getNo, goodsDO.getNo())
                .or()
                .eq(GoodsDO::getName, goodsDO.getName())
                .eq(GoodsDO::getGoodsType, goodsDO.getGoodsType()));

        // 校验编码是否重复
        long noCount = goodsDOList.stream().filter(goods -> goods.getNo().equals(goodsDO.getNo())).count();
        if (noCount > 0) {
            throw new BusinessException("当前商品信息编码已被占用，请重新添加");
        }

        // 校验商品名称是否重复
        long nameCount = goodsDOList.stream().filter(goods -> goods.getName().equals(goodsDO.getName())).count();
        if (nameCount > 0) {
            throw new BusinessException("当前商品信息已存在");
        }

        return goodsDO;
    }

    /**
     * 创建商品唯一编码
     *
     * @param goodsDO 商品信息
     */
    private void createGoodsNo(GoodsDO goodsDO) {
        // 商品格式：NO. + 时间戳 + goodsType + currencyType
        String goodsType = Optional.ofNullable(goodsDO.getGoodsType()).orElse(0) > 9 ? "" + goodsDO.getGoodsType() : "0" + goodsDO.getGoodsType();
        String currencyType = Optional.ofNullable(goodsDO.getCurrencyType()).orElse(0) > 9 ? "" + goodsDO.getCurrencyType() : "0" + goodsDO.getCurrencyType();
        goodsDO.setNo("NO" + DateUtil.now().replaceAll("[-|:| ]", "").trim() + goodsType + currencyType);
    }
}
