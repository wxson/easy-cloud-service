package com.easy.cloud.web.service.order.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GR
 * @date 2021-11-27 11:32
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class WxDecodeResource {

  private String original_type;
  private String algorithm;
  private String ciphertext;
  private String associated_data;
  private String nonce;
}
