package com.easy.cloud.web.component.mysql.utils;

import com.easy.cloud.web.component.core.util.SnowflakeUtils;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author GR
 * @date 2023/10/24 17:19
 */
public class IdGeneratorUtil implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
      Object o) throws HibernateException {
    return SnowflakeUtils.next();
  }
}
