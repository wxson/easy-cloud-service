package com.easy.cloud.web.service.cms.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.cloud.web.service.cms.biz.domain.db.GlobalConfDO;

/**
 * @author GR
 */
public interface IGlobalConfService extends IService<GlobalConfDO> {

    /**
     * 根据全局配置key获取对应的值
     *
     * @param key 全局配置key
     * @return java.lang.String
     */
    String getGlobalConfValueByKey(String key);

}
