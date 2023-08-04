package com.easy.cloud.web.service.upms.biz.converter;

import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Menu转换器
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
public class MenuConverter {

  /**
   * DTO转为DO
   *
   * @param menu 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.MenuDO
   */
  public static MenuDO convertTo(MenuDTO menu) {
    return MenuDO.builder()
        .id(menu.getId())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param menu 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  public static MenuVO convertTo(MenuDO menu) {
    return MenuVO.builder()
        .id(menu.getId())
        .createBy(menu.getCreateBy())
        .createAt(menu.getCreateAt())
        .updateAt(menu.getUpdateAt())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param menus 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  public static List<MenuVO> convertTo(List<MenuDO> menus) {
    return menus.stream()
        .map(MenuConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  public static Page<MenuVO> convertTo(Page<MenuDO> page) {
    return page.map(MenuConverter::convertTo);
  }
}