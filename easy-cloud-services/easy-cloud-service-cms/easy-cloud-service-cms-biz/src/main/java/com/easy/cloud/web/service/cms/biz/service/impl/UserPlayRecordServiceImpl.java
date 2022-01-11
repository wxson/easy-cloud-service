package com.easy.cloud.web.service.cms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.service.cms.biz.domain.db.UserPlayRecordDO;
import com.easy.cloud.web.service.cms.biz.mapper.DbUserPlayRecordMapper;
import com.easy.cloud.web.service.cms.biz.service.IUserPlayRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户对局记录
 *
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserPlayRecordServiceImpl extends ServiceImpl<DbUserPlayRecordMapper, UserPlayRecordDO> implements IUserPlayRecordService {

}
