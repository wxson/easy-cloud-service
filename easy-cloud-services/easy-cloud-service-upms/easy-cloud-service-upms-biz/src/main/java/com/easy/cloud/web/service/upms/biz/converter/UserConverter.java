package com.easy.cloud.web.service.upms.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
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
    UserDO userDO = UserDO.builder().build();
    BeanUtils.copyProperties(user, userDO, true);
    return userDO;
  }

  /**
   * DO转为VO
   *
   * @param user 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  public static UserVO convertTo(UserDO user) {
    UserVO userVO = UserVO.builder().build();
    BeanUtils.copyProperties(user, userVO, true);
    return userVO;
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