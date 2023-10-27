package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.service.upms.api.dto.DepartmentDTO;
import com.easy.cloud.web.service.upms.api.vo.DepartmentVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Department interface
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public interface IDepartmentService {

  /**
   * 新增数据
   *
   * @param departmentDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  DepartmentVO save(DepartmentDTO departmentDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param departmentDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  DepartmentVO update(DepartmentDTO departmentDTO);

  /**
   * 根据ID删除数据
   *
   * @param departmentId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String departmentId);

  /**
   * 根据ID获取详情
   *
   * @param departmentId 对象ID
   * @return java.lang.Boolean
   */
  DepartmentVO detailById(String departmentId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.DepartmentVO> 返回列表数据
   */
  List<DepartmentVO> list();

  /**
   * 获取部门树
   *
   * @return
   */
  List<Tree<String>> tree();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.api.vo.DepartmentVO> 返回列表数据
   */
  Page<DepartmentVO> page(int page, int size);

}