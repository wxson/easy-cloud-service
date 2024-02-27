package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Repository
public interface UserRepository extends JpaLogicRepository<UserDO, String> {

    /**
     * 根据账号获取用户信息
     *
     * @param userName 账号信息
     * @return
     */
    Optional<UserDO> findByUserName(String userName);

    /**
     * 匹配数据，任意一个条件符合即可
     *
     * @param unionId 唯一标识
     * @return
     */
    Optional<UserDO> findByUnionId(String unionId);

    /**
     * 匹配数据，任意一个条件符合即可
     *
     * @param tel 电话
     * @return
     */
    Optional<UserDO> findByTel(String tel);
}