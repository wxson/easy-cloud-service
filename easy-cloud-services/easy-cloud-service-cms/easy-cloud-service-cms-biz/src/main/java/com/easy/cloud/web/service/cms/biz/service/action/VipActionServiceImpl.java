package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.db.VipGoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.db.VipGoodsReceiveDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.mapper.DbVipGoodsMapper;
import com.easy.cloud.web.service.cms.biz.mapper.DbVipGoodsReceiveMapper;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.cms.biz.service.IVipGoodsService;
import com.easy.cloud.web.service.member.api.domain.vo.MemberVO;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * VIP免费领取
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class VipActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final IVipGoodsService vipGoodsService;

    private final DbVipGoodsReceiveMapper vipGoodsReceiveMapper;

    private final DbVipGoodsMapper vipGoodsMapper;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        // 校验当前玩家是否满足VIP条件
        // 当前用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 获取会员信息
        MemberVO memberVO = memberFeignClientService.getMemberDetailByUserId(userId).getData();
        // 获取VIP等级
        Integer vipLevel = Optional.ofNullable(memberVO.getVipLevel()).orElse(GlobalConstants.ZERO);
        VipGoodsDO vipGoodsDO = vipGoodsService.getOne(Wrappers.<VipGoodsDO>lambdaQuery().eq(VipGoodsDO::getVipLevel, vipLevel));
        if (Objects.isNull(vipGoodsDO)) {
            throw new BusinessException("当前VIP奖励暂无信息");
        }

        // 若当前商品为领取商品，则表示当前商品为VIP绑定商品，否则为VIP每日领取商品
        if (vipGoodsDO.getGoodsNo().equals(actionDTO.getGoodsNo())) {
            // 获取VIP奖励领取情况
            VipGoodsReceiveDO vipGoodsReceiveDO = vipGoodsReceiveMapper.selectOne(Wrappers.<VipGoodsReceiveDO>lambdaQuery()
                    .eq(VipGoodsReceiveDO::getGoodsNo, vipGoodsDO.getGoodsNo())
                    .eq(VipGoodsReceiveDO::getMemberId, userId));
            // 未领取
            if (Objects.isNull(vipGoodsReceiveDO)) {
                vipGoodsReceiveMapper.insert(VipGoodsReceiveDO.builder().goodsNo(vipGoodsDO.getGoodsNo()).memberId(userId).build());
            }
        } else {
            // 获取VIP绑定商品
            GoodsDO vipBindGoodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getNo, vipGoodsDO.getGoodsNo()));
            if (Objects.isNull(vipBindGoodsDO)) {
                throw new BusinessException("VIP绑定的商品信息有误");
            }

            // 获取VIP绑定每日领取商品
            GoodsDO dailyGoodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getNo, vipBindGoodsDO.getDailyGoodsNo()));
            if (Objects.isNull(dailyGoodsDO)) {
                throw new BusinessException("VIP绑定的每日领取商品信息有误");
            }

            // 判断当前是否已领取过
            Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), dailyGoodsDO.getNo()));
            if (Objects.nonNull(o)) {
                throw new BusinessException("当日已领取");
            }
        }


        // 当天有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
                TimeUnit.MINUTES);
        // 奖品发放
        this.grantPrize(actionDTO, goodsService, memberFeignClientService);
        return true;
    }

    @Override
    public Object list(ActionDTO actionDTO) {
        // 当前用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 获取会员信息
        MemberVO memberVO = memberFeignClientService.getMemberDetailByUserId(userId).getData();
        // 获取下一等级VIP需要充值的数据
        MemberVO nextVipDiamondData = memberFeignClientService.calculateNextVipNeedDiamond(userId).getData();

        // 获取VIP等级
        Integer vipLevel = Optional.ofNullable(memberVO.getVipLevel()).orElse(GlobalConstants.ZERO);
        // 下一VIP等级
        Integer nextVipLevel = vipLevel + 1;
        // 获取VIP绑定的商品信息
        List<VipGoodsDO> vipGoodsDOList = vipGoodsService.list(Wrappers.<VipGoodsDO>lambdaQuery().in(VipGoodsDO::getVipLevel, CollUtil.newArrayList(vipLevel, nextVipLevel)));
        if (CollUtil.isEmpty(vipGoodsDOList)) {
            return CollUtil.newArrayList();
        }
        // 构建VIP等级Map
        Map<Integer, String> vipLevelGoodsNoMap = vipGoodsDOList.stream().collect(Collectors.toMap(VipGoodsDO::getVipLevel, VipGoodsDO::getGoodsNo, (v1, v2) -> v1));
        // 获取VIP商品No
        List<String> vipGoodsNoList = vipGoodsDOList.stream().map(VipGoodsDO::getGoodsNo).collect(Collectors.toList());

        // 获取所有参与VIP活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }

        // 立即获取的商品
        List<GoodsDO> immediatelyGoodsDOList = goodsDOList.stream()
                .filter(goodsDO -> vipGoodsNoList.contains(goodsDO.getNo())).collect(Collectors.toList());
        // 每日领取的商品编码集合
        List<String> dailyGoodsNoList = immediatelyGoodsDOList.stream().map(GoodsDO::getDailyGoodsNo).collect(Collectors.toList());

        // 每日领取的商品
        List<GoodsDO> dailyGoodsDOList = goodsDOList.stream().filter(goodsDO -> dailyGoodsNoList.contains(goodsDO.getNo())).collect(Collectors.toList());
        Map<String, GoodsDO> dailyGoodsNoMap = dailyGoodsDOList.stream().collect(Collectors.toMap(GoodsDO::getNo, goodsDO -> goodsDO, (g1, g2) -> g1));

        // 获取当前VIP等级对应的商品编码
        String vipLevelBindGoodsNo = vipLevelGoodsNoMap.get(vipLevel);
        String nextVipLevelBindGoodsNo = vipLevelGoodsNoMap.get(nextVipLevel);
        Optional<GoodsDO> optionalGoodsDO = immediatelyGoodsDOList.stream().filter(goodsDO -> goodsDO.getNo().equals(nextVipLevelBindGoodsNo)).findFirst();
        String nextVipDailyGoodsNo = "";
        if (optionalGoodsDO.isPresent()) {
            nextVipDailyGoodsNo = optionalGoodsDO.get().getDailyGoodsNo();
        }

        for (GoodsDO goodsDO : immediatelyGoodsDOList) {
            // 当前VIP等级绑定的商品
            if (StrUtil.isNotBlank(vipLevelBindGoodsNo) && vipLevelBindGoodsNo.equals(goodsDO.getNo())) {
                // 构建返回数据
                ActionVO actionVO = goodsDO.convertTo(ActionVO.class).setGoodsNo(goodsDO.getNo()).setHandle(false);
                // 获取对应的每日礼包商品
                String dailyGoodsNo = goodsDO.getDailyGoodsNo();
                if (StrUtil.isNotBlank(dailyGoodsNo)) {
                    String finalNextVipDailyGoodsNo = nextVipDailyGoodsNo;
                    Optional.ofNullable(dailyGoodsNoMap.get(dailyGoodsNo))
                            .ifPresent(dailyGoodsDO -> {
                                JSONObject jsonObject = new JSONObject();
                                if (Objects.nonNull(nextVipDiamondData)) {
                                    // 设置当前会员充值的钻石数
                                    jsonObject.putOpt("currentRechargeTotalDiamondNum", nextVipDiamondData.getCurrentRechargeTotalDiamondNum());
                                    // 下一VIP等级所需钻石数
                                    int offsetDiamondNum = NumberUtil.sub(nextVipDiamondData.getNextVipLevelDiamondNum(), nextVipDiamondData.getCurrentRechargeTotalDiamondNum()).intValue();
                                    // 设置下一会员等级需要消耗的钻石数
                                    jsonObject.putOpt("nextVipLevelNeedDiamondNum", Math.max(offsetDiamondNum, 0));
                                }

                                // 当前每日商品编码
                                jsonObject.set("goodsNo", dailyGoodsNo);
                                // 可操作
                                jsonObject.set("handle", true);
                                // 当前VIP等级
                                jsonObject.set("vipLevel", vipLevel);
                                Optional.ofNullable(dailyGoodsNoMap.get(dailyGoodsNo))
                                        .ifPresent(dailyGoods -> {
                                            // 当前VIP等级可领取的金币数
                                            jsonObject.set("vipLevelGoldCoinNum", dailyGoods.getGoldCoinNum());
                                        });
                                // 下一VIP等级
                                jsonObject.set("nextVipLevel", nextVipLevel);
                                if (StrUtil.isNotBlank(finalNextVipDailyGoodsNo)) {
                                    Optional.ofNullable(dailyGoodsNoMap.get(finalNextVipDailyGoodsNo))
                                            .ifPresent(dailyGoods -> {
                                                // 下一VIP等级可领取的金币数
                                                jsonObject.set("nextVipLevelGoldCoinNum", dailyGoods.getGoldCoinNum());
                                            });
                                }
                                // 判断当前是否已领取过
                                Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), dailyGoodsNo));
                                if (Objects.nonNull(o)) {
                                    // 不可操作
                                    jsonObject.set("handle", false);
                                }
                                actionVO.setSpecialData(jsonObject);
                            });
                }
                return actionVO;
            }
        }
        return null;
    }
}
