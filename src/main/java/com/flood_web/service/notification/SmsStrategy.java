package com.flood_web.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
public class SmsStrategy implements NotifyStrategy{

    @Autowired
    private SnsClient snsClient;

    @Override
    public void notifyEvent(String destination, String message) {

        PublishRequest request = PublishRequest.builder()
                .phoneNumber(destination)
                .message(message)
                .build();
        snsClient.publish(request);
    }
}
