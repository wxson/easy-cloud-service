package com.easy.cloud.web.service.upms.biz.converter;

import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * User转换器
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
public class UserConverter {

  /**
   * DTO转为DO
   *
   * @param user 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.UserDO
   */
  public static UserDO convertTo(UserDTO user) {
    return UserDO.builder()
        .id(user.getId())
        .country(user.getCountry())
        .unionId(user.getUnionId())
        .city(user.getCity())
        .nickName(user.getNickName())
        .sex(user.getSex())
        .avatar(user.getAvatar())
        .userName(user.getUserName())
        .appleId(user.getAppleId())
        .password(user.getPassword())
        .deleted(user.getDeleted())
        .province(user.getProvince())
        .identity(user.getIdentity())
        .tenantId(user.getTenantId())
        .tel(user.getTel())
        .region(user.getRegion())
        .account(user.getAccount())
        .email(user.getEmail())
        .status(user.getStatus())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param user 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  public static UserVO convertTo(UserDO user) {
    return UserVO.builder()
        .id(user.getId())
        .createBy(user.getCreateBy())
        .createAt(user.getCreateAt())
        .updateAt(user.getUpdateAt())
        .country(user.getCountry())
        .unionId(user.getUnionId())
        .city(user.getCity())
        .nickName(user.getNickName())
        .sex(user.getSex())
        .avatar(user.getAvatar())
        .userName(user.getUserName())
        .appleId(user.getAppleId())
        .password(user.getPassword())
        .deleted(user.getDeleted())
        .province(user.getProvince())
        .identity(user.getIdentity())
        .tenantId(user.getTenantId())
        .tel(user.getTel())
        .region(user.getRegion())
        .account(user.getAccount())
        .email(user.getEmail())
        .status(user.getStatus())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param users 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  public static List<UserVO> convertTo(List<UserDO> users) {
    return users.stream()
        .map(UserConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  public static Page<UserVO> convertTo(Page<UserDO> page) {
    return page.map(UserConverter::convertTo);
  }
}