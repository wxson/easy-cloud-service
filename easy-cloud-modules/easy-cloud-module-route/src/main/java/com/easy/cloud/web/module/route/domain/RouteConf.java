package com.easy.cloud.web.module.route.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConvertProxy;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 路由信息表
 *
 * @author GR
 * @TableName route_conf
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName("route_conf")
public class RouteConf implements IConvertProxy {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 断言，匹配，格式为JSON 字符串数组
     * 如：Path=/goods/**；网关自动匹配当前请求path，若包含/goods，则会自动匹配到mall-goods服务，并uri为lb://mall-goods的服务
     */
    private String predicates;
    /**
     * 路由ID，可自定义，不写系统会自动按一定算法进行赋值
     */
    private String routeId;

    /**
     * 过滤规则
     */
    private String filters;

    /**
     * 断言，匹配，格式为JSON 字符串数组
     */
    private String uri;

    /**
     * 断言，匹配，格式为JSON 字符串数组
     */
    private String routeName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer deleted;

    /**
     * 创建用户
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RouteConf other = (RouteConf) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPredicates() == null ? other.getPredicates() == null : this.getPredicates().equals(other.getPredicates()))
                && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
                && (this.getFilters() == null ? other.getFilters() == null : this.getFilters().equals(other.getFilters()))
                && (this.getUri() == null ? other.getUri() == null : this.getUri().equals(other.getUri()))
                && (this.getRouteName() == null ? other.getRouteName() == null : this.getRouteName().equals(other.getRouteName()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
                && (this.getCreatorAt() == null ? other.getCreatorAt() == null : this.getCreatorAt().equals(other.getCreatorAt()))
                && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()))
                && (this.getUpdateAt() == null ? other.getUpdateAt() == null : this.getUpdateAt().equals(other.getUpdateAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPredicates() == null) ? 0 : getPredicates().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getFilters() == null) ? 0 : getFilters().hashCode());
        result = prime * result + ((getUri() == null) ? 0 : getUri().hashCode());
        result = prime * result + ((getRouteName() == null) ? 0 : getRouteName().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCreatorAt() == null) ? 0 : getCreatorAt().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getUpdateAt() == null) ? 0 : getUpdateAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", predicates=").append(predicates);
        sb.append(", routeId=").append(routeId);
        sb.append(", filters=").append(filters);
        sb.append(", uri=").append(uri);
        sb.append(", routeName=").append(routeName);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append(", creatorAt=").append(creatorAt);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}