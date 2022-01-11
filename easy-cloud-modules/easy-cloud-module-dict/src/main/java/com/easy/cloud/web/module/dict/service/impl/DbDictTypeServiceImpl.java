package com.easy.cloud.web.module.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.module.dict.domain.DictTypeDO;
import com.easy.cloud.web.module.dict.mapper.DbDictTypeMapper;
import com.easy.cloud.web.module.dict.service.IDictTypeService;
import org.springframework.stereotype.Service;

/**
 * @author GR
 */
@Service
public class DbDictTypeServiceImpl extends ServiceImpl<DbDictTypeMapper, DictTypeDO> implements IDictTypeService {

}
