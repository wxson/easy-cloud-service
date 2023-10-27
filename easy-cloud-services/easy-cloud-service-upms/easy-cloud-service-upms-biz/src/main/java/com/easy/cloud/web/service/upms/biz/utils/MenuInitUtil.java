package com.easy.cloud.web.service.upms.biz.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

/**
 * 菜单初始化管理
 *
 * @author GR
 * @date 2023/8/4 16:45
 */
@Slf4j
@UtilityClass
public class MenuInitUtil {

  /**
   * 初始化系统菜单
   *
   * @return
   */
  public List<MenuDO> initSystemDefaultMenus() {
    try {
      File file = ResourceUtils.getFile("classpath:json/sys_menu.json");
      // 读取系统菜单数据
      String sysMenuJsonStr = IoUtil.read(IoUtil.toStream(file)).toString();
      return JSONUtil.toList(sysMenuJsonStr, MenuDO.class);
    } catch (FileNotFoundException fileNotFoundException) {
      log.error("init system menu fail：{}", fileNotFoundException.getMessage());
    }
    return new ArrayList<>();
  }
}
