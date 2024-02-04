package com.easy.cloud.web.component.core.entity;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

/**
 * @author GR
 * @date 2024-1-15 15:28
 */
@Data
public class BasePage {

  /**
   * 当前页
   */
  private Integer page;
  /**
   * 分页大小
   */
  private Integer size;

  public Integer getPage() {
    return Math.max(GlobalCommonConstants.ZERO, Objects.isNull(page) ? 0 : page - 1);
  }

  public Integer getSize() {
    return Objects.isNull(size) ? 10 : size;
  }

  public Pageable pageable() {
    return PageRequest.of(this.getPage(), this.getSize());
  }
}
