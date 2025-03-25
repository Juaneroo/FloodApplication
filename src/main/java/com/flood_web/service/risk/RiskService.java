package com.flood_web.service.risk;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.controller.Sensor;
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
                    smsStrategy.notifyEvent(
                            "+57" + personUnderRisk.getTelephone(),
                            MessageTemplate.TEMPLATE_1_SMS
                                    .replace("[name]", personUnderRisk.getName())
                                    .replace("[risk]", riskLevel.getDescription())
                    );
                    callStrategy.notifyEvent(
                            "+57" + personUnderRisk.getTelephone(),
                            MessageTemplate.TEMPLATE_1_CALL
                                    .replace("[name]", personUnderRisk.getName())
                                    .replace("[risk]", riskLevel.getDescription())
                    );
                }
                );
    }

    private void handleNotificationsAsync(Sensor sensorUnderRisk, RiskLevel riskLevel){
        new Thread(() -> {
            handleNotifications(sensorUnderRisk, riskLevel);
        }).start();
    }
}
