package com.flood_web.service.cache;

import com.flood_web.service.risk.RiskLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service for tracking sent notifications in Redis cache to prevent duplicate notifications
 * within a time frame.
 */
@Slf4j
@Service
public class UniqueKeyCacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String NOTIFICATION_KEY_PREFIX = "notification:";
    private static final String RISK_LEVEL_KEY_PREFIX = "risk_level:";

    /**
     * Checks if a notification has been sent for the given sensor and phone number.
     * Also checks if the current risk level is higher than the last notified risk level.
     *
     * @param sensorId The ID of the sensor
     * @param phoneNumber The phone number
     * @param currentRiskLevel The current risk level
     * @return true if a notification has been sent and the risk level hasn't increased
     */
    public boolean hasNotificationBeenSent(String sensorId, String phoneNumber, RiskLevel currentRiskLevel) {
        String notificationKey = NOTIFICATION_KEY_PREFIX + sensorId + ":" + phoneNumber;
        String riskLevelKey = RISK_LEVEL_KEY_PREFIX + sensorId + ":" + phoneNumber;
        
        // Check if notification exists
        Boolean exists = redisTemplate.hasKey(notificationKey);
        if (Boolean.FALSE.equals(exists)) {
            return false;
        }

        // Get the last notified risk level
        String lastRiskLevelStr = redisTemplate.opsForValue().get(riskLevelKey);
        if (lastRiskLevelStr == null) {
            return false;
        }

        try {
            RiskLevel lastRiskLevel = RiskLevel.valueOf(lastRiskLevelStr);
            // If current risk level is higher than the last notified one, we should notify again
            return currentRiskLevel.getLevel() <= lastRiskLevel.getLevel();
        } catch (IllegalArgumentException e) {
            log.error("Invalid risk level stored in cache: {}", lastRiskLevelStr);
            return false;
        }
    }

    /**
     * Marks a notification as sent for the given sensor and phone number.
     * Stores both the notification status and the risk level.
     *
     * @param sensorId The ID of the sensor
     * @param phoneNumber The phone number
     * @param expirationHours The expiration time in hours
     * @param riskLevel The risk level of the notification
     */
    public void markNotificationAsSent(String sensorId, String phoneNumber, long expirationHours, RiskLevel riskLevel) {
        String notificationKey = NOTIFICATION_KEY_PREFIX + sensorId + ":" + phoneNumber;
        String riskLevelKey = RISK_LEVEL_KEY_PREFIX + sensorId + ":" + phoneNumber;

        // Store notification status
        redisTemplate.opsForValue().set(notificationKey, "true", expirationHours, TimeUnit.HOURS);
        
        // Store risk level with the same expiration
        redisTemplate.opsForValue().set(riskLevelKey, riskLevel.name(), expirationHours, TimeUnit.HOURS);
        
        log.info("Marked notification as sent for sensor {} and phone {} with risk level {}", 
                sensorId, phoneNumber, riskLevel);
    }
} 