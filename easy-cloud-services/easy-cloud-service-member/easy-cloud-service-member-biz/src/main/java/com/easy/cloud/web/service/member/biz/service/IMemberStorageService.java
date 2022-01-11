package com.easy.cloud.web.service.member.biz.service;

import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.member.biz.domain.db.MemberStorageDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberStorageDTO;

/**
 * @author GR
 */
public interface IMemberStorageService extends IRepositoryService<MemberStorageDO> {

    /**
     * 存储金额
     *
     * @param memberStorageDTO 上传条件
     * @return java.lang.Boolean
     */
    Boolean saveAmount(MemberStorageDTO memberStorageDTO);

    /**
     * 取出金币
     *
     * @param memberStorageDTO 取出数据
     * @return java.lang.Boolean
     */
    Boolean takeOutAmount(MemberStorageDTO memberStorageDTO);

    /**
     * 初始化会员仓库
     *
     * @param userId 用户ID
     * @return com.easy.cloud.web.service.member.biz.domain.db.MemberStorageDO
     */
    MemberStorageDO initMemberStorage(String userId);
}
