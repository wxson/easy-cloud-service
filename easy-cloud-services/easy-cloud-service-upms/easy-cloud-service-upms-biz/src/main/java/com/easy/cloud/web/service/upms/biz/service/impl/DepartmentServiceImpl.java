package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.easy.cloud.web.service.upms.api.dto.DepartmentDTO;
import com.easy.cloud.web.service.upms.api.vo.DepartmentVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsConstants;
import com.easy.cloud.web.service.upms.biz.converter.DepartmentConverter;
import com.easy.cloud.web.service.upms.biz.domain.DepartmentDO;
import com.easy.cloud.web.service.upms.biz.repository.DepartmentRepository;
import com.easy.cloud.web.service.upms.biz.service.IDepartmentService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Department 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements IDepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  @Override
  @Transactional
  public DepartmentVO save(DepartmentDTO departmentDTO) {
    // 转换成DO对象
    DepartmentDO department = DepartmentConverter.convertTo(departmentDTO);
    // TODO 校验逻辑

    // 存储
    departmentRepository.save(department);
    // 转换对象
    return DepartmentConverter.convertTo(department);
  }

  @Override
  @Transactional
  public DepartmentVO update(DepartmentDTO departmentDTO) {
    // 转换成DO对象
    DepartmentDO department = DepartmentConverter.convertTo(departmentDTO);
    if (Objects.isNull(department.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    departmentRepository.save(department);
    // 转换对象
    return DepartmentConverter.convertTo(department);
  }

  @Override
  @Transactional
  public Boolean removeById(Long departmentId) {
    // TODO 业务逻辑校验

    // 删除
    departmentRepository.deleteById(departmentId);
    return true;
  }

  @Override
  public DepartmentVO detailById(Long departmentId) {
    // TODO 业务逻辑校验

    // 删除
    DepartmentDO department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return DepartmentConverter.convertTo(department);
  }

  @Override
  public List<DepartmentVO> list() {
    // 获取列表数据
    List<DepartmentDO> departments = departmentRepository.findAll();
    return DepartmentConverter.convertTo(departments);
  }

  @Override
  public List<Tree<Long>> tree() {
    List<DepartmentVO> departmentVOS = this.list();
    return TreeUtil.build(departmentVOS, UpmsConstants.DEPART_TREE_ROOT_ID, (menu, tree) -> {
      Map<String, Object> departMap = BeanUtil.beanToMap(menu);
      tree.setId(menu.getId());
      tree.setName(menu.getName());
      tree.setParentId(menu.getParentId());
      tree.putAll(departMap);
    });
  }

  @Override
  public Page<DepartmentVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return DepartmentConverter.convertTo(departmentRepository.findAll(pageable));
  }
}