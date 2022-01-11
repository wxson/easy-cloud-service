package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.member.biz.domain.db.MemberDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberPropertyRecordDTO;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberPropertyRecordVO;

import java.util.List;

/**
 * @author GR
 */

public interface IMemberService extends IRepositoryService<MemberDO> {

    /**
     * 根据消费水平更新当前VIP等级
     *
     * @param memberDO 资产记录
     */
    void updateMemberVipLevel(MemberDO memberDO);

    /**
     * 插入会员资产记录
     *
     * @param memberDTO 资产记录
     */
    void insertMemberPropertyRecord(MemberDTO memberDTO);

    /**
     * 查询会员资产记录
     *
     * @param memberPropertyRecordDTO 查询条件
     * @return java.util.List<com.easy.cloud.web.service.member.biz.domain.vo.MemberPropertyRecordVO>
     */
    List<MemberPropertyRecordVO> memberPropertyRecordList(MemberPropertyRecordDTO memberPropertyRecordDTO);
}
