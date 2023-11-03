package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.DeptDTO;
import com.easy.cloud.web.service.upms.api.vo.DeptVO;
import com.easy.cloud.web.service.upms.biz.converter.DeptConverter;
import com.easy.cloud.web.service.upms.biz.domain.DeptDO;
import com.easy.cloud.web.service.upms.biz.repository.DeptRepository;
import com.easy.cloud.web.service.upms.biz.service.IDeptService;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * dept 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Slf4j
@Service
public class DeptServiceImpl implements IDeptService {

  @Autowired
  private DeptRepository deptRepository;

  @Override
  public void init() {
    // 未初始化过数据
    if (deptRepository.count() <= 0) {
      // 初始化部门数据
      List<DeptDO> depts = this.initJsonToList("json/sys_dept.json", DeptDO.class);
      deptRepository.saveAll(depts);
      log.info("init platform depts content success!");
    }
  }

  @Override
  @Transactional
  public DeptVO save(DeptDTO deptDTO) {
    // 转换成DO对象
    DeptDO dept = DeptConverter.convertTo(deptDTO);
    // TODO 校验逻辑

    // 存储
    deptRepository.save(dept);
    // 转换对象
    return DeptConverter.convertTo(dept);
  }

  @Override
  @Transactional
  public DeptVO update(DeptDTO deptDTO) {
    // 更新操作，ID不能为空
    if (Objects.isNull(deptDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // 若父级ID为空，则设置默认父级ID
    if (StrUtil.isBlank(deptDTO.getParentId())) {
      deptDTO.setParentId(GlobalCommonConstants.DEPART_TREE_ROOT_ID);
    }
    // 获取当前部门信息
    DeptDO deptDO = deptRepository.findById(deptDTO.getId())
        .orElseThrow(() -> new BusinessException("当前部门信息不存在"));
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(deptDTO, deptDO, true);
    // 存储
    deptRepository.save(deptDO);
    // 转换对象
    return DeptConverter.convertTo(deptDO);
  }

  @Override
  @Transactional
  public Boolean removeById(String deptId) {
    // TODO 业务逻辑校验
    // 判断当前部门下是否存在子部门
    if (deptRepository.countByParentId(deptId) > 0) {
      throw new BusinessException("当前部门下存在子部门，请先删除子部门");
    }
    // 删除
    deptRepository.logicDelete(deptId);
    return true;
  }

  @Override
  public DeptVO detailById(String deptId) {
    // TODO 业务逻辑校验

    // 删除
    DeptDO dept = deptRepository.findById(deptId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return DeptConverter.convertTo(dept);
  }

  @Override
  public List<DeptVO> list() {
    // 获取列表数据
    List<DeptDO> depts = deptRepository.findAll();
    return DeptConverter.convertTo(depts);
  }

  @Override
  public List<Tree<String>> tree() {
    return TreeUtil
        .build(this.list(), GlobalCommonConstants.DEPART_TREE_ROOT_ID, (dept, tree) -> {
          tree.setId(dept.getId());
          tree.setName(dept.getName());
          tree.setParentId(dept.getParentId());
          tree.setWeight(dept.getSort());
          tree.putAll(BeanUtil.beanToMap(dept));
        });
  }

  @Override
  public Page<DeptVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return DeptConverter.convertTo(deptRepository.findAll(pageable));
  }
}