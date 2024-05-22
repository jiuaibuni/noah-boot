package com.gengby.starter.captcha.behavior.autoconfigure.cache;

import com.anji.captcha.service.CaptchaCacheService;
import com.gengby.starter.cache.redisson.util.RedisUtils;
import com.gengby.starter.captcha.behavior.enums.StorageType;

import java.time.Duration;

/**
 * 行为验证码 Redis 缓存实现
 *
 * @author Bull-BCLS
 * @since 1.1.0
 */
public class BehaviorCaptchaCacheServiceImpl implements CaptchaCacheService {
    @Override
    public void set(String key, String value, long expiresInSeconds) {
        RedisUtils.set(key, value, Duration.ofSeconds(expiresInSeconds));
    }

    @Override
    public boolean exists(String key) {
        return RedisUtils.hasKey(key);
    }

    @Override
    public void delete(String key) {
        RedisUtils.delete(key);
    }

    @Override
    public String get(String key) {
        return RedisUtils.get(key);
    }

    @Override
    public String type() {
        return StorageType.REDIS.name().toLowerCase();
    }
}
