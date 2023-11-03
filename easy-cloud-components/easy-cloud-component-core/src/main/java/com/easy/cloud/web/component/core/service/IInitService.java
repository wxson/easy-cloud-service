package com.easy.cloud.web.component.core.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;

/**
 * 初始化设置
 *
 * @author GR
 * @date 2021-4-15 13:23
 */
public interface IInitService {

  /**
   * 初始化
   */
  void init();

  /**
   * 初始化数据集
   *
   * @param path   JSON文件路径
   * @param tClass 目标对象
   * @param <T>    返回对象类型
   * @return
   */
  default <T> List<T> initJsonToList(String path, Class<T> tClass) {
    try {
      // 读取JSON数据
      File file = ResourceUtils.getFile("classpath:" + path);
      // 读取系统菜单数据
      String sysMenuJsonStr = IoUtil.read(IoUtil.toStream(file)).toString();
      return JSONUtil.toList(sysMenuJsonStr, tClass);
    } catch (FileNotFoundException fileNotFoundException) {
    }
    return new ArrayList<>();
  }

  /**
   * 初始化数据对象
   *
   * @param path   JSON文件路径
   * @param tClass 目标对象
   * @param <T>    返回对象类型
   * @return
   */
  default <T> T initJsonToBean(String path, Class<T> tClass) {
    try {
      // 读取JSON数据
      File file = ResourceUtils.getFile("classpath:" + path);
      // 读取系统菜单数据
      String sysMenuJsonStr = IoUtil.read(IoUtil.toStream(file)).toString();
      return JSONUtil.toBean(sysMenuJsonStr, tClass);
    } catch (FileNotFoundException fileNotFoundException) {
    }
    return null;
  }
}
