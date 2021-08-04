package com.easy.cloud.web.component.mysql.controller;

import com.easy.cloud.web.component.mysql.service.IRepositoryService;

/**
 * Controller基类
 * 说明：封装BaseController类的初衷只是为了去除重复的简单的业务常见逻辑的耦合性，并不是为实现一本万利，万能的控制类，
 * 该方式只适用于普通的常见的CRUD等常见的业务逻辑，且更适用于单表业务，当需要执行连表查询时，需维护BaseMapper的三个方法
 * <p>
 * QueryCondition：查询条件对象。一般为前端查询条件的集合
 * BaseForm：表单对象。一般为表对象的副本，前端上传的参数
 * Entity：表对象。一般对应一个表结构
 *
 * @author GR
 * @date 2021-4-20 9:16
 */
public interface IController<Entity> {

    /**
     * 当前页
     */
    String CURRENT = "current";

    /**
     * 每页数据大小
     */
    String SIZE = "size";

    /**
     * 获取Service类
     *
     * @return com.baomidou.mybatisplus.extension.service.IService<Entity>
     */
    IRepositoryService<Entity> getService();
}
