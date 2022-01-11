package com.easy.cloud.web.service.member.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.member.biz.domain.db.MemberBackpackDO;
import com.easy.cloud.web.service.member.biz.mapper.DbMemberBackpackMapper;
import com.easy.cloud.web.service.member.biz.service.IMemberBackpackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberBackpackServiceImpl extends ServiceImpl<DbMemberBackpackMapper, MemberBackpackDO> implements IMemberBackpackService {

    @Override
    public MemberBackpackDO verifyBeforeSave(MemberBackpackDO memberBackpackDO) {
        MemberBackpackDO backpackDO = this.getOne(Wrappers.<MemberBackpackDO>lambdaQuery().eq(MemberBackpackDO::getUserId, memberBackpackDO.getUserId())
                .eq(MemberBackpackDO::getGoodsNo, memberBackpackDO.getGoodsNo()));
        if (null != backpackDO) {
            throw new BusinessException("当前商品信息已存在");
        }

        return memberBackpackDO;
    }
}
