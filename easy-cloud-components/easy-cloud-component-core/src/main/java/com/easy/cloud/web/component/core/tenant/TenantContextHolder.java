package com.easy.cloud.web.component.core.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @author 杨朝勇
 * @date 2018/10/4 租户工具类
 */
@UtilityClass
public class TenantContextHolder {

    private final ThreadLocal<String> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();

    /**
     * TTL 设置租户ID<br/>
     * <b>谨慎使用此方法,避免嵌套调用。尽量使用 {@code TenantBroker} </b>
     *
     * @param tenantId 租户ID
     */
    public void setTenantId(String tenantId) {
        THREAD_LOCAL_TENANT.set(tenantId);
    }

    /**
     * 获取TTL中的租户ID
     */
    public String getTenantId() {
        return THREAD_LOCAL_TENANT.get();
    }

    /**
     * 移除租户ID
     */
    public void clear() {
        THREAD_LOCAL_TENANT.remove();
    }

}
