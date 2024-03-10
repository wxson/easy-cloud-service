package com.easy.cloud.web.module.protocol.biz.converter;

import com.easy.cloud.web.module.protocol.api.dto.ProtocolDTO;
import com.easy.cloud.web.module.protocol.api.vo.ProtocolVO;
import com.easy.cloud.web.module.protocol.biz.domain.ProtocolDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Protocol 转换器
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
public class ProtocolConverter {

    /**
     * DTO转为DO
     *
     * @param protocol 转换数据
     * @return com.easy.cloud.web.module.protocol.biz.domain.ProtocolDO
     */
    public static ProtocolDO convertTo(ProtocolDTO protocol) {
        return ProtocolDO.builder()
                .id(protocol.getId())
                .uniqueId(protocol.getUniqueId())
                .name(protocol.getName())
                .description(protocol.getDescription())
                .remark(protocol.getRemark())
                .content(protocol.getContent())
                .build();
    }

    /**
     * DO转为VO
     *
     * @param protocol 转换数据
     * @return com.easy.cloud.web.module.protocol.api.domain.vo.ProtocolVO
     */
    public static ProtocolVO convertTo(ProtocolDO protocol) {
        return ProtocolVO.builder()
                .id(protocol.getId())
                .uniqueId(protocol.getUniqueId())
                .name(protocol.getName())
                .description(protocol.getDescription())
                .remark(protocol.getRemark())
                .content(protocol.getContent())
                .build();
    }

    /**
     * 列表DO转为VO
     *
     * @param protocols 转换数据
     * @return com.easy.cloud.web.module.protocol.api.domain.vo.ProtocolVO
     */
    public static List<ProtocolVO> convertTo(List<ProtocolDO> protocols) {
        return protocols.stream()
                .map(ProtocolConverter::convertTo)
                .collect(Collectors.toList());
    }

    /**
     * 分页DO转为VO
     *
     * @param page 转换数据
     * @return com.easy.cloud.web.module.protocol.api.domain.vo.ProtocolVO
     */
    public static Page<ProtocolVO> convertTo(Page<ProtocolDO> page) {
        return page.map(ProtocolConverter::convertTo);
    }
}