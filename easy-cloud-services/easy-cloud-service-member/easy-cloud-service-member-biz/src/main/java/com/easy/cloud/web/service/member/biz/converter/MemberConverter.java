package com.easy.cloud.web.service.member.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.member.api.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.domain.MemberDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Member转换器
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
public class MemberConverter {

  /**
   * DTO转为DO
   *
   * @param member 转换数据
   * @return com.easy.cloud.web.service.member.api.db.MemberDO
   */
  public static MemberDO convertTo(MemberDTO member) {
    MemberDO memberDO = MemberDO.builder().build();
    BeanUtils.copyProperties(member, memberDO, true);
    return memberDO;
  }

  /**
   * DO转为VO
   *
   * @param member 转换数据
   * @return com.easy.cloud.web.service.member.api.vo.MemberVO
   */
  public static MemberVO convertTo(MemberDO member) {
    MemberVO memberVO = MemberVO.builder().build();
    BeanUtils.copyProperties(member, memberVO, true);
    return memberVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param members 转换数据
   * @return com.easy.cloud.web.service.member.api.vo.MemberVO
   */
  public static List<MemberVO> convertTo(List<MemberDO> members) {
    return members.stream()
        .map(MemberConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.member.api.vo.MemberVO
   */
  public static Page<MemberVO> convertTo(Page<MemberDO> page) {
    return page.map(MemberConverter::convertTo);
  }
}