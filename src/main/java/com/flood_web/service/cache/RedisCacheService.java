package com.flood_web.service.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service for handling Redis cache operations.
 */
@Service
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String EMPTY_VALUE = "";

    public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Saves a key in Redis with an empty value.
     *
     * @param key The unique key to save
     * @param expirationTimeInSeconds The time in seconds after which the key should expire
     */
    public void saveKey(String key, long expirationTimeInSeconds) {
        redisTemplate.opsForValue().set(key, EMPTY_VALUE, expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Checks if a key exists in Redis.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Deletes a key from Redis.
     *
     * @param key The key to delete
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
} 