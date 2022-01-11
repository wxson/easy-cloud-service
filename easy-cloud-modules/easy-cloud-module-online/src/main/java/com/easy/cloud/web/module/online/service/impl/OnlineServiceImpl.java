package com.easy.cloud.web.module.online.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.module.online.constants.OnlineConstants;
import com.easy.cloud.web.module.online.service.IOnlineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * 在线人数逻辑
 *
 * @author GR
 * @date 2021-12-21 9:46
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineServiceImpl implements IOnlineService {

    private final RedisTemplate redisTemplate;

    @Override
    public Integer addOnlineNumber(Integer number) {
        return this.addOnLineNumber(number, OnlineConstants.ONLINE_USER_NUMBER_KEY);
    }

    @Override
    public Integer removeOnlineNumber(Integer number) {
        return this.removeOnLineNumber(number, OnlineConstants.ONLINE_USER_NUMBER_KEY);
    }

    @Override
    public Integer getOnlineNumber() {
        // 获取当前在线人数
        return this.readRedisStore(int.class, OnlineConstants.ONLINE_USER_NUMBER_KEY).orElse(GlobalConstants.ZERO);
    }

    @Override
    public Integer addOnLineNumber(Integer number, CharSequence keyCharSequence, Object... objects) {
        number = Optional.ofNullable(number).orElse(GlobalConstants.ZERO);
        // 获取当前在线人数
        int newOnlineNumber = this.getOnlineNumber(keyCharSequence, objects) + number;
        // 更新新的在线人数
        this.saveRedisStore(newOnlineNumber, keyCharSequence, objects);
        return newOnlineNumber;
    }

    @Override
    public Integer removeOnLineNumber(Integer number, CharSequence keyCharSequence, Object... objects) {
        number = Optional.ofNullable(number).orElse(GlobalConstants.ZERO);
        Integer onlineNumber = this.getOnlineNumber(keyCharSequence, objects);
        int newOnlineNumber = onlineNumber - number;
        newOnlineNumber = Math.max(newOnlineNumber, GlobalConstants.ZERO);
        // 更新新的在线人数
        this.saveRedisStore(newOnlineNumber, keyCharSequence, objects);
        return newOnlineNumber;
    }

    @Override
    public Integer getOnlineNumber(CharSequence keyCharSequence, Object... objects) {
        // 获取当前在线人数
        return this.readRedisStore(int.class, keyCharSequence, objects).orElse(GlobalConstants.ZERO);
    }

    @Override
    public void saveRedisStore(Object data, CharSequence keyCharSequence, Object... objects) {
        redisTemplate.opsForValue().set(StrUtil.format(keyCharSequence, objects), data);
    }

    @Override
    public <T> Optional<T> readRedisStore(Class<T> tClass, CharSequence keyCharSequence, Object... objects) {
        Object o = redisTemplate.opsForValue().get(StrUtil.format(keyCharSequence, objects));
        if (Objects.nonNull(o)) {
            if (tClass.isPrimitive()) {
                return (Optional<T>) Optional.of(o);
            }
            return Optional.of(JSONUtil.toBean(JSONUtil.toJsonStr(o), tClass));
        }

        return Optional.empty();
    }

}
