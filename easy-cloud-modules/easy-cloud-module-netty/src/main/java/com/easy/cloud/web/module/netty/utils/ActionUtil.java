package com.easy.cloud.web.module.netty.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author GR
 * @date 2023/5/18 18:10
 */
@Slf4j
@UtilityClass
public class ActionUtil {

  /**
   * 获取Action 唯一路径
   *
   * @param paths
   * @return
   */
  public String getUniqueActionPath(String... paths) {
    StringBuffer buffer = new StringBuffer("/");
    for (String path : paths) {
      if (StringUtils.isEmpty(path)) {
        continue;
      }
      if (path.contains("?")) {
        path = path.substring(0, path.indexOf("?"));
      }
      buffer.append(path).append("/");
    }
    return formatPath(buffer.toString());
  }

  /**
   * 格式化路径
   *
   * @param path 待格式化的路径
   * @return
   */
  public String formatPath(String path) {
    return path.replaceAll("/+", "/");
  }
}
