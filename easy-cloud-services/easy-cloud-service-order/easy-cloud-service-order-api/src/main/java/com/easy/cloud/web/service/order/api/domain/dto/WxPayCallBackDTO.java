package com.easy.cloud.web.service.order.api.domain.dto;

import com.easy.cloud.web.service.order.api.domain.WxDecodeResource;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 微信支付回调对象
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class WxPayCallBackDTO {

  private String id;
  private String create_time;
  private String resource_type;
  private String event_type;
  private String summary;
  private WxDecodeResource resource;
}