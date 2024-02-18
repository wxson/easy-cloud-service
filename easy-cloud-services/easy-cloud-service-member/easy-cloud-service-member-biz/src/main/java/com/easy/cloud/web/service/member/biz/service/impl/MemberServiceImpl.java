package com.easy.cloud.web.service.member.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.converter.MemberConverter;
import com.easy.cloud.web.service.member.biz.domain.MemberDO;
import com.easy.cloud.web.service.member.biz.repository.MemberRepository;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    @Transactional(rollbackOn = Exception.class)
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
    @Transactional(rollbackOn = Exception.class)
    public MemberVO update(MemberDTO memberDTO) {
        // 转换成DO对象
        if (Objects.isNull(memberDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        MemberDO member = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new BusinessException("当前信息不存在"));
        // 将修改的数据赋值给数据库数据
        BeanUtils.copyProperties(memberDTO, member, true);
        // TODO 业务逻辑校验

        // 更新
        memberRepository.save(member);
        // 转换对象
        return MemberConverter.convertTo(member);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
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
    public MemberVO detailByUserId(String userId) {
        MemberDO member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换成VO
        MemberVO memberVO = MemberConverter.convertTo(member);
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
    @Transactional(rollbackOn = Exception.class)
    public MemberVO initMember() {
        // 获取登录用户信息
        AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
        // 根据用户ID获取会员信息
        Optional<MemberDO> memberOptional = memberRepository.findByUserId(authenticationUser.getId());
        if (memberOptional.isPresent()) {
            return MemberConverter.convertTo(memberOptional.get());
        }
        // 获取会员信息
        MemberDO memberDO = MemberDO.builder()
                .userId(authenticationUser.getId())
                .nickName(authenticationUser.getUsername())
                .build();
        memberRepository.save(memberDO);
        return MemberConverter.convertTo(memberDO);
    }
}