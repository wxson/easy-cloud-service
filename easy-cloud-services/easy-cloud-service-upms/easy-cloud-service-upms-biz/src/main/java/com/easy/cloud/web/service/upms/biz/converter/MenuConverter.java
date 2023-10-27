package com.easy.cloud.web.service.upms.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
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
    MenuDO menuDO = MenuDO.builder().build();
    BeanUtils.copyProperties(menu, menuDO, true);
    return menuDO;
  }

  /**
   * DO转为VO
   *
   * @param menu 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  public static MenuVO convertTo(MenuDO menu) {
    MenuVO menuVO = MenuVO.builder().build();
    BeanUtils.copyProperties(menu, menuVO, true);
    return menuVO;
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