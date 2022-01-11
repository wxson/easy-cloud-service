package com.easy.cloud.web.module.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.module.dict.domain.DictDO;
import com.easy.cloud.web.module.dict.mapper.DbDictMapper;
import com.easy.cloud.web.module.dict.service.IDictService;
import org.springframework.stereotype.Service;

/**
 * @author GR
 */
@Service
public class DbDictServiceImpl extends ServiceImpl<DbDictMapper, DictDO> implements IDictService {

}
