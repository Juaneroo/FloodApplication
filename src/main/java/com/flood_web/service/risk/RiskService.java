package com.flood_web.service.risk;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.controller.Sensor;
import com.flood_web.service.cache.UniqueKeyCacheService;
import com.flood_web.service.crud.FamilyMembersCrudService;
import com.flood_web.service.crud.SensorCrudService;
import com.flood_web.service.notification.CallStrategy;
import com.flood_web.service.notification.SmsStrategy;
import com.flood_web.service.notification.WppStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class RiskService {

    @Autowired
    private SensorCrudService sensorCrudService;

    @Autowired
    private FamilyMembersCrudService familyMembersCrudService;

    @Autowired
    private RiskLevelEvaluator riskLevelEvaluator;

    @Autowired
    private SmsStrategy smsStrategy;

    @Autowired
    private CallStrategy callStrategy;

    @Autowired
    private WppStrategy wppStrategy;

    @Autowired
    private UniqueKeyCacheService uniqueKeyCacheService;

    /**
     * Gets the expiration time in hours based on the risk level.
     *
     * @param riskLevel The risk level enum
     * @return The expiration time in hours
     */
    private long getExpirationHours(RiskLevel riskLevel) {
        return switch (riskLevel) {
            case CONSIDERABLE -> 12;
            case HIGH -> 8;
            case VERY_HIGH -> 6;
            case IMMINENT_DANGER -> 4;
            case EXTREME -> 2;
            case DISASTER -> 1;
            default -> throw new IllegalArgumentException("Invalid risk level for notification: " + riskLevel);
        };
    }

    public void handleRisk(String riverId, int currentLevel){
        Optional<Sensor> sensorOptional = sensorCrudService.findByIdToEvaluate(riverId);
        if(sensorOptional.isEmpty()){
            log.warn("Sensor with id {} does not exist. Risk can not be handled.", riverId);
            return;
        }
        Sensor sensor = sensorOptional.get();
        if(!sensor.getActive()){
            log.warn("Sensor with id {} and name {} is not active. Risk can not be handled.", riverId, sensor.getName());
            return;
        }

        if(!riskLevelEvaluator.shouldNotify(currentLevel, sensor.getRiskExpression())){
            log.warn("Sensor with id {} and name {} is not under risk", riverId, sensor.getName());
            return;
        }

        handleNotificationsAsync(sensor, riskLevelEvaluator.getRiskLevel(currentLevel, sensor.getRiskExpression()));
    }

    public void handleNotifications(Sensor sensorUnderRisk, RiskLevel riskLevel){
        Set<FamilyMembers> peopleForNotification = familyMembersCrudService.findPeopleUnderRisk(sensorUnderRisk.getId());

        peopleForNotification.forEach(
                (personUnderRisk) -> {
                    String phoneNumber = "+57" + personUnderRisk.getTelephone();
                    
                    // Check if we've already sent a notification for this sensor-phone combination
                    // and if the current risk level is higher than the last notified one
                    if (!uniqueKeyCacheService.hasNotificationBeenSent(sensorUnderRisk.getId(), phoneNumber, riskLevel)) {
                        // Send notifications
                        smsStrategy.notifyEvent(
                                phoneNumber,
                                MessageTemplate.TEMPLATE_1_SMS
                                        .replace("[name]", personUnderRisk.getName())
                                        .replace("[risk]", riskLevel.getDescription())
                        );
                        callStrategy.notifyEvent(
                                phoneNumber,
                                MessageTemplate.TEMPLATE_1_CALL
                                        .replace("[name]", personUnderRisk.getName())
                                        .replace("[risk]", riskLevel.getDescription())
                        );
                        
                        // Calculate expiration time based on risk level and mark notification as sent
                        long expirationHours = NotificationExpiration.getExpirationHours(riskLevel);
                        uniqueKeyCacheService.markNotificationAsSent(sensorUnderRisk.getId(), phoneNumber, expirationHours, riskLevel);
                        log.info("Notification sent for sensor {} and phone {} with risk level {} and {} hours expiration", 
                                sensorUnderRisk.getId(), phoneNumber, riskLevel, expirationHours);
                    } else {
                        log.info("Skipping notification for sensor {} and phone {} as it was already sent for a higher or equal risk level", 
                                sensorUnderRisk.getId(), phoneNumber);
                    }
                }
        );
    }

    private void handleNotificationsAsync(Sensor sensorUnderRisk, RiskLevel riskLevel){
        new Thread(() -> {
            handleNotifications(sensorUnderRisk, riskLevel);
        }).start();
    }
}
