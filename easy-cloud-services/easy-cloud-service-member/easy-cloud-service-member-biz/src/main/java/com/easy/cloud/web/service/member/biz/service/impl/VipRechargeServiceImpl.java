package com.easy.cloud.web.service.member.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.service.member.biz.domain.db.VipRechargeDO;
import com.easy.cloud.web.service.member.biz.mapper.DbVipRechargeMapper;
import com.easy.cloud.web.service.member.biz.service.IVipRechargeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class VipRechargeServiceImpl extends ServiceImpl<DbVipRechargeMapper, VipRechargeDO> implements IVipRechargeService {

}
