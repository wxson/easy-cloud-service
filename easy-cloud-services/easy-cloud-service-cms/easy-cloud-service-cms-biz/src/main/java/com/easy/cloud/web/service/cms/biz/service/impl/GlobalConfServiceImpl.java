package com.easy.cloud.web.service.cms.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.constants.CacheConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GlobalConfDO;
import com.easy.cloud.web.service.cms.biz.mapper.DbGlobalConfMapper;
import com.easy.cloud.web.service.cms.biz.service.IGlobalConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 全局配置业务逻辑
 *
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class GlobalConfServiceImpl extends ServiceImpl<DbGlobalConfMapper, GlobalConfDO> implements IGlobalConfService {

    @Override
    @Cacheable(value = CacheConstants.GLOBAL_CONF_KEY, key = "#key", unless = "#result == null")
    public String getGlobalConfValueByKey(String key) {
        GlobalConfDO globalConfDO = this.getOne(Wrappers.<GlobalConfDO>lambdaQuery().eq(GlobalConfDO::getConfKey, key));
        if (StrUtil.isBlank(globalConfDO.getConfValue())) {
            return "";
        }
        return globalConfDO.getConfValue();
    }
}
