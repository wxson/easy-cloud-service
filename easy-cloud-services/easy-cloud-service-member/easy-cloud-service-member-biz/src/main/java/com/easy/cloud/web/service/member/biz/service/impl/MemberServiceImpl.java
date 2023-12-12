package com.easy.cloud.web.service.member.biz.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.member.api.dto.MemberBalanceDTO;
import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.converter.MemberConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberDO;
import com.easy.cloud.web.service.member.biz.repository.MemberRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Member 业务逻辑
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Slf4j
@Service
public class MemberServiceImpl implements IMemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private UpmsFeignClientService upmsFeignClientService;

  @Override
  @Transactional
  public MemberVO save(MemberDTO memberDTO) {
    // 转换成DO对象
    MemberDO member = MemberConverter.convertTo(memberDTO);
    // TODO 校验逻辑

    // 存储
    memberRepository.save(member);
    // 转换对象
    return MemberConverter.convertTo(member);
  }

  @Override
  @Transactional
  public MemberVO update(MemberDTO memberDTO) {
    // 转换成DO对象
    MemberDO member = MemberConverter.convertTo(memberDTO);
    if (Objects.isNull(member.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    memberRepository.save(member);
    // 转换对象
    return MemberConverter.convertTo(member);
  }

  @Override
  public MemberVO updateProfile(MemberDTO memberDTO) {
    // 校验个性签名
    if (StrUtil.isBlank(memberDTO.getProfile())) {
      throw new BusinessException("个性签名不能为空");
    }
    // 会员信息
    MemberDO memberDO = memberRepository.findById(memberDTO.getId())
        .orElseThrow(() -> new BusinessException("当前会员信息不存在"));
    memberDO.setProfile(memberDTO.getProfile());
    memberRepository.save(memberDO);
    return MemberConverter.convertTo(memberDO);
  }

  @Override
  @Transactional
  public Boolean removeById(String memberId) {
    // TODO 业务逻辑校验

    // 删除
    memberRepository.deleteById(memberId);
    return true;
  }

  @Override
  public MemberVO detailById(String memberId) {
    // TODO 业务逻辑校验

    // 删除
    MemberDO member = memberRepository.findById(memberId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return MemberConverter.convertTo(member);
  }

  @Override
  public MemberVO getMemberDetailByUserId(String userId) {
    MemberDO member = memberRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换成VO
    MemberVO memberVO = MemberConverter.convertTo(member);
    // 获取用户详情
    UserVO user = upmsFeignClientService.loadUserByUserId(userId).getData();
    if (null != user) {
      memberVO.setAccount(user.getUserName())
          .setNickName(user.getNickName())
          .setAvatar(user.getAvatar());
    }
    // TODO 胜率 MVP次数
    // TODO 英雄IP
    return memberVO;
  }

  @Override
  public List<MemberVO> list() {
    // 获取列表数据
    List<MemberDO> members = memberRepository.findAll();
    return MemberConverter.convertTo(members);
  }

  @Override
  public Page<MemberVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return MemberConverter.convertTo(memberRepository.findAll(pageable));
  }

  @Override
  public MemberVO initMemberInfo(MemberDTO memberDTO) {
    // 根据用户ID获取会员信息
    Optional<MemberDO> memberOptional = memberRepository.findByUserId(memberDTO.getUserId());
    if (memberOptional.isPresent()) {
      return MemberConverter.convertTo(memberOptional.get());
    }
    // 获取会员信息
    MemberDO memberDO = MemberConverter.convertTo(memberDTO);
    memberRepository.save(memberDO);
    return MemberConverter.convertTo(memberOptional.get());
  }

  @Override
  public MemberVO updateMemberProperty(MemberBalanceDTO memberBalanceDTO) {
    // 获取当前会员信息
    MemberDO memberDO = memberRepository.findByUserId(memberBalanceDTO.getUserId())
        .orElseThrow(() -> new RuntimeException("当前会员信息不存在"));
    // 资产累加
    Optional.ofNullable(memberBalanceDTO.getAmount()).ifPresent(
        amount -> memberDO.setAmount(NumberUtil.add(amount, memberDO.getAmount()).intValue()));
    Optional.ofNullable(memberBalanceDTO.getDiamond()).ifPresent(
        diamond -> memberDO.setDiamond(NumberUtil.add(diamond, memberDO.getDiamond()).intValue()));
    Optional.ofNullable(memberBalanceDTO.getCoupon()).ifPresent(
        coupon -> memberDO.setCoupon(NumberUtil.add(coupon, memberDO.getCoupon()).intValue()));
    memberRepository.save(memberDO);
    return MemberConverter.convertTo(memberDO);
  }
}