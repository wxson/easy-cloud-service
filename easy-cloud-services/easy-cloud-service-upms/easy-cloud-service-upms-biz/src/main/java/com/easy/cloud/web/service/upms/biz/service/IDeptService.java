package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.DeptDTO;
import com.easy.cloud.web.service.upms.api.vo.DeptVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Department interface
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public interface IDeptService extends IInitService {

  /**
   * 新增数据
   *
   * @param deptDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  DeptVO save(DeptDTO deptDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param deptDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  DeptVO update(DeptDTO deptDTO);

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
  DeptVO detailById(String departmentId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.DepartmentVO> 返回列表数据
   */
  List<DeptVO> list();

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
  Page<DeptVO> page(int page, int size);

}