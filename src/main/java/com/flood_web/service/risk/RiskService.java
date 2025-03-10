package com.flood_web.service.risk;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.controller.River;
import com.flood_web.controller.Sensor;
import com.flood_web.service.FamilyMembersCrudService;
import com.flood_web.service.SensorCrudService;
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

        handleNotifications(sensor);

    }

    public void handleNotifications(Sensor sensorUnderRisk){

        Set<FamilyMembers> peopleForNotification = familyMembersCrudService.findPeopleUnderRisk(sensorUnderRisk.getId());

        peopleForNotification.forEach(
                personUnderRisk -> log.info("Notifying {} to the number {}", personUnderRisk.getName(), personUnderRisk.getTelephone())
        );
    }
}
