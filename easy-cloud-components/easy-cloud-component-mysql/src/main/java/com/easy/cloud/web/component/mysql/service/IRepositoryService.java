package com.easy.cloud.web.component.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 重写IService接口类，新增before after操作
 *
 * @author GR
 * @date 2021-4-20 11:54
 */
public interface IRepositoryService<Entity> extends IService<Entity>, IOperationTreeService<Entity> {

}
