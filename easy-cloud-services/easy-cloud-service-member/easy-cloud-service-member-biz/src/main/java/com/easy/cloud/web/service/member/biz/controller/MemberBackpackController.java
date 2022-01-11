package com.easy.cloud.web.service.member.biz.controller;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.biz.domain.db.MemberBackpackDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberBackpackDTO;
import com.easy.cloud.web.service.member.biz.domain.query.MemberBackpackQuery;
import com.easy.cloud.web.service.member.biz.service.IMemberBackpackService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GR
 * @date 2021-11-28 18:02
 */
@Slf4j
@RestController
@RequestMapping("member/backpack")
@AllArgsConstructor
@Api(value = "会员背包管理", tags = "会员背包管理")
public class MemberBackpackController extends BaseController<MemberBackpackQuery, MemberBackpackDTO, MemberBackpackDO> {

    private final IMemberBackpackService memberBackpackService;

    @Override
    public IRepositoryService<MemberBackpackDO> getService() {
        return memberBackpackService;
    }

    /**
     * 将商品插入会员背包
     * 注：此时的商品只能是牌背、桌布等可见的物品，如钻石，金币，则不仅如此背包
     *
     * @param goodsNo 商品信息
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("insert/goods/{goodsNo}")
    HttpResult<Boolean> insertGoodsInBackpack(@PathVariable(value = "goodsNo") String goodsNo) {
        if (StrUtil.isBlank(goodsNo)) {
            throw new BusinessException("商品编码不能为空");
        }
        memberBackpackService.save(MemberBackpackDO.build().setUserId(SecurityUtils.getAuthenticationUser().getId()).setGoodsNo(goodsNo).setStatus(StatusEnum.START_STATUS.getCode()));
        return HttpResult.ok(true);
    }
}
