package com.easy.cloud.web.module.protocol.biz.service;

import com.easy.cloud.web.module.protocol.api.dto.ProtocolDTO;
import com.easy.cloud.web.module.protocol.api.vo.ProtocolVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Protocol interface
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
public interface IProtocolService {

    /**
     * 新增数据
     *
     * @param protocolDTO 保存参数
     * @return com.easy.cloud.web.module.protocol.api.vo.ProtocolVO
     */
    ProtocolVO save(ProtocolDTO protocolDTO);

    /**
     * 更新数据，默认全量更新
     *
     * @param protocolDTO 保存参数
     * @return com.easy.cloud.web.module.protocol.api.vo.ProtocolVO
     */
    ProtocolVO update(ProtocolDTO protocolDTO);

    /**
     * 根据ID删除数据
     *
     * @param protocolId 对象ID
     * @return java.lang.Boolean
     */
    Boolean removeById(String protocolId);

    /**
     * 根据ID获取详情
     *
     * @param protocolId 对象ID
     * @return java.lang.Boolean
     */
    ProtocolVO detailById(String protocolId);

    /**
     * 根据协议唯一标识获取详情
     *
     * @param protocolUniqueId 协议唯一标识
     * @return com.easy.cloud.web.module.protocol.api.vo.ProtocolVO
     */
    ProtocolVO appDetailByUniqueId(String protocolUniqueId);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.module.protocol.api.vo.ProtocolVO> 返回列表数据
     */
    List<ProtocolVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param page 当前页
     * @param size 每页大小
     * @return List<com.easy.cloud.web.module.protocol.api.vo.ProtocolVO> 返回列表数据
     */
    Page<ProtocolVO> page(int page, int size);
}