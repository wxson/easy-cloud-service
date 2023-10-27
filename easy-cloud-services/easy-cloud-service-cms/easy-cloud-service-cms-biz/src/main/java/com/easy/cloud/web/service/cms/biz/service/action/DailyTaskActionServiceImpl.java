package com.easy.cloud.web.service.cms.biz.service.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.biz.constants.CmsConstants;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.db.UserPlayRecordDO;
import com.easy.cloud.web.service.cms.biz.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ActionVO;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.cms.biz.service.IUserPlayRecordService;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每日任务
 *
 * @author GR
 * @date 2021-11-28 15:58
 */
@Slf4j
@Service
@AllArgsConstructor
public class DailyTaskActionServiceImpl implements IActionService {

    private final IGoodsService goodsService;

    private final IUserPlayRecordService userPlayRecordService;

    private final MemberFeignClientService memberFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object handle(ActionDTO actionDTO) {
        if (StrUtil.isBlank(actionDTO.getGoodsNo())) {
            throw new BusinessException("当前商品编码不能为空");
        }

        // 获取所有参与活动的商品
        GoodsDO goodsDO = goodsService.getOne(Wrappers.<GoodsDO>lambdaQuery()
                .eq(GoodsDO::getAction, actionDTO.getAction().getCode())
                .eq(GoodsDO::getNo, actionDTO.getGoodsNo()));
        if (Objects.isNull(goodsDO)) {
            throw new BusinessException("获取当前活动商品信息失败");
        }

        // 暂时不校验是否有资格领取


        // 判断当前是否已领取过
        Object o = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()));
        if (Objects.nonNull(o)) {
            throw new BusinessException("今日已领取");
        }

        // 当天有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), actionDTO.getGoodsNo()),
                true,
                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
                TimeUnit.MINUTES);

        // 插入活跃度
        this.insertUserActive(goodsDO.getAliveness());
        // 发放奖励
        this.grantPrize(actionDTO, goodsService, memberFeignClientService);
        return true;
    }

    /**
     * 插入活跃度
     *
     * @param activeOffset 活跃度
     */
    private void insertUserActive(Integer activeOffset) {
        // 获取当前活跃度
        int currentActive = (int) Optional.ofNullable(redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.USER_ACTIVE_KEY, SecurityUtils.getAuthenticationUser().getId()))).orElse(0);
        // 当天有效
        redisTemplate.opsForValue().set(StrUtil.format(CmsConstants.USER_ACTIVE_KEY, SecurityUtils.getAuthenticationUser().getId()),
                activeOffset + currentActive,
                DateUtil.between(DateUtil.date(), DateUtil.endOfDay(DateUtil.date()), DateUnit.MINUTE),
                TimeUnit.MINUTES);
    }

    @Override
    public Object list(ActionDTO actionDTO) {
        // 获取所有参与首冲活动的商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery().eq(GoodsDO::getAction, actionDTO.getAction().getCode()));
        if (CollUtil.isEmpty(goodsDOList)) {
            return CollUtil.newArrayList();
        }

        // 当天开始时间
        String minCreateAt = DateUtil.beginOfDay(DateUtil.date()).toString();
        // 当天结束时间
        String maxCreateAt = DateUtil.endOfDay(DateUtil.date()).toString();

        // 获取今日所有的对局记录
        List<UserPlayRecordDO> userPlayRecordDOList = userPlayRecordService.list(Wrappers.<UserPlayRecordDO>lambdaQuery()
                .eq(UserPlayRecordDO::getPlayerId, SecurityUtils.getAuthenticationUser().getId())
                .ge(UserPlayRecordDO::getCreateAt, minCreateAt)
                .lt(UserPlayRecordDO::getCreateAt, maxCreateAt));
        // 获取当前赢取的资产
        Long currentWinAmountNum = this.getCurrentWinAmountNum(userPlayRecordDOList);
        // 获取当前的胜利对局数量
        Integer currentWinPlayNum = this.getCurrentWinPlayNum(userPlayRecordDOList);
        // 获取当前完成对局
        Integer currentCompletePlayNum = this.getCurrentCompletePlayNum(userPlayRecordDOList);
        // 获取当前最大翻数
        Integer currentMaxRateNum = this.getCurrentMaxRateNum(userPlayRecordDOList);
        // 获取当前分享数
        Integer currentShareNum = this.getCurrentShareNum();

        CollUtil.sort(goodsDOList, Comparator.comparing(GoodsDO::getSort));
        return goodsDOList.stream()
                .sorted(Comparator.comparing(GoodsDO::getSort))
                .map(goodsDO -> {
                    ActionVO actionVO = goodsDO.convertTo(ActionVO.class)
                            .setGoodsNo(goodsDO.getNo()).setGoodsName(goodsDO.getName()).setHandle(false).setForbid(false).setCurrentValue(
                            GlobalCommonConstants.ZERO);
                    // 设置值
                    // 设置当前赢取的金币数
                    if (actionVO.getGoodsName().contains("每日累计赢取")) {
                        actionVO.setCurrentValue(currentWinAmountNum.intValue());
                    } else
                        // 设置当前胜利的对局数
                        if (actionVO.getGoodsName().contains("每日胜利")) {
                            actionVO.setCurrentValue(currentWinPlayNum);
                        } else
                            // 设置当前完成的对局数
                            if (actionVO.getGoodsName().contains("每日完成")) {
                                actionVO.setCurrentValue(currentCompletePlayNum);
                            } else
                                // 设置当前分享的次数
                                if (actionVO.getGoodsName().contains("每日分享一次")) {
                                    actionVO.setCurrentValue(currentShareNum);
                                    // 注释分享
                                    return null;
                                } else
                                    // 设置当前的最大翻数
                                    if (actionVO.getGoodsName().contains("麻将最大番数")) {
                                        actionVO.setCurrentValue(currentMaxRateNum);
                                    }

                    // 如果到达触发值，则可领取
                    if (actionVO.getCurrentValue() >= actionVO.getTriggerValue()) {
                        actionVO.setHandle(true).setCurrentValue(Math.min(actionVO.getCurrentValue(), actionVO.getTriggerValue()));
                    }

                    // 判断当前是否已领取过
                    Object received = redisTemplate.opsForValue().get(StrUtil.format(CmsConstants.FREE_RECEIVE_GOODS_KEY, SecurityUtils.getAuthenticationUser().getId(), goodsDO.getNo()));
                    if (Objects.nonNull(received)) {
                        // 不可操作
                        actionVO.setHandle(true).setForbid(true);
                    }

                    return actionVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前赢取的金额
     *
     * @param userPlayRecordDOList 对局记录
     * @return java.lang.Integer
     */
    private Long getCurrentWinAmountNum(List<UserPlayRecordDO> userPlayRecordDOList) {
        // 总金额
        return userPlayRecordDOList.stream()
                .filter(userPlayRecordDO -> 0 == userPlayRecordDO.getAction())
                .mapToLong(UserPlayRecordDO::getAmount)
                .sum();
    }

    /**
     * 获取当前胜利的对局数
     *
     * @param userPlayRecordDOList 当日对局记录
     * @return java.lang.Integer
     */
    private Integer getCurrentWinPlayNum(List<UserPlayRecordDO> userPlayRecordDOList) {
        // 对局结束后，若为增加金币，表示当前对局为胜利
        long count = userPlayRecordDOList.stream().filter(userPlayRecordDO -> 0 == userPlayRecordDO.getAction()).count();
        return Long.valueOf(count).intValue();
    }

    /**
     * 设置当前完成的对局数
     *
     * @param userPlayRecordDOList 当日对局记录
     * @return java.lang.Integer
     */
    private Integer getCurrentCompletePlayNum(List<UserPlayRecordDO> userPlayRecordDOList) {
        return userPlayRecordDOList.size();
    }

    /**
     * 设置当前分享的次数
     *
     * @return java.lang.Integer
     */
    private Integer getCurrentShareNum() {
        return GlobalCommonConstants.ZERO;
    }

    /**
     * 设置当前的最大翻数
     *
     * @param userPlayRecordDOList 当日对局记录
     * @return java.lang.Integer
     */
    private Integer getCurrentMaxRateNum(List<UserPlayRecordDO> userPlayRecordDOList) {
        Optional<UserPlayRecordDO> userPlayRecordDOOptional = userPlayRecordDOList.stream().max(Comparator.comparing(UserPlayRecordDO::getMaxRate));
        if (userPlayRecordDOOptional.isPresent()) {
            return userPlayRecordDOOptional.get().getMaxRate();
        }

        return GlobalCommonConstants.ZERO;
    }
}
