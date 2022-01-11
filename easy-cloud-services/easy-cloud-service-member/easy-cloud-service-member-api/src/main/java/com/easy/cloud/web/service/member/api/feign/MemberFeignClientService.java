package com.easy.cloud.web.service.member.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.domain.dto.MemberPropertyRecordDTO;
import com.easy.cloud.web.service.member.api.domain.vo.MemberPropertyRecordVO;
import com.easy.cloud.web.service.member.api.domain.vo.MemberVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.MEMBER_SERVICE)
public interface MemberFeignClientService {

    /**
     * 根据用户ID获取会员信息
     *
     * @param userId 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @GetMapping("member/detail/{userId}")
    HttpResult<MemberVO> getMemberDetailByUserId(@PathVariable(value = "userId") String userId);


    /**
     * 根据用户ID获取会员信息
     *
     * @param memberDTO 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @PostMapping("member/detail")
    HttpResult<MemberVO> getMemberDetail(@RequestBody MemberDTO memberDTO);

    /**
     * 修改会员信息
     *
     * @param memberDTO 会员信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @GetMapping("member/update")
    HttpResult<MemberVO> updateMemberInfo(@RequestBody MemberDTO memberDTO);

    /**
     * 修改会员资产信息
     *
     * @param memberDTO 会员信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @PostMapping("member/property/update")
    HttpResult<MemberVO> updateMemberProperty(@RequestBody MemberDTO memberDTO);

    /**
     * 计算当前会员需要充值多少钻石可升下一个VIP
     *
     * @param userId 会员用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @GetMapping("member/calculate/next/vip/need/diamond/{userId}")
    @ApiOperation(value = "计算当前会员需要充值多少钻石可升下一个VIP")
    HttpResult<MemberVO> calculateNextVipNeedDiamond(@PathVariable(value = "userId") String userId);

    /**
     * 会员资产记录查询条件
     *
     * @param memberPropertyRecordDTO 查询条件
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @PostMapping("member/property/record/list")
    HttpResult<List<MemberPropertyRecordVO>> memberPropertyRecordList(@RequestBody MemberPropertyRecordDTO memberPropertyRecordDTO);

    /**
     * 修改会员资产信息
     *
     * @param memberDTO 会员信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.domain.vo.MemberVO>
     */
    @PostMapping("member/init")
    HttpResult<MemberVO> initMemberInfo(@RequestBody MemberDTO memberDTO);


    /**
     * 设置仓库使用权限
     *
     * @param status 仓库状态
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("member/storage/enable/permission/{status}")
    HttpResult<Boolean> enableStoragePermission(@PathVariable(value = "status") Integer status);


    /**
     * 将商品插入会员背包
     * 注：此时的商品只能是牌背、桌布等可见的物品，如钻石，金币，则不仅如此背包
     *
     * @param goodsNo 商品信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("member/backpack/insert/goods/{goodsNo}")
    HttpResult<Boolean> insertGoodsInBackpack(@PathVariable(value = "goodsNo") String goodsNo);
}
