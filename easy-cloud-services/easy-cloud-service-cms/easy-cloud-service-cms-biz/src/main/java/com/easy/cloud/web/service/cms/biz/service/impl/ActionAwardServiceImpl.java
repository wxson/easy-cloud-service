package com.easy.cloud.web.service.cms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.service.cms.biz.domain.db.ActionAwardDO;
import com.easy.cloud.web.service.cms.biz.mapper.DbActionAwardMapper;
import com.easy.cloud.web.service.cms.biz.service.IActionAwardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class ActionAwardServiceImpl extends ServiceImpl<DbActionAwardMapper, ActionAwardDO> implements IActionAwardService {

}
