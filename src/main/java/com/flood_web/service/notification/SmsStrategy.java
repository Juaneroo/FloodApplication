package com.flood_web.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Slf4j
@Component
public class SmsStrategy implements NotifyStrategy{

    //@Autowired
    //private SnsClient snsClient;

    /*@Override
    public void notifyEvent(String destination, String message) {

        PublishRequest request = PublishRequest.builder()
                .phoneNumber(destination)
                .message(message)
                .build();
        snsClient.publish(request);
    }*/

    @Override
    public void notifyEvent(String destination, String message) {
        try {
            Message.creator(new com.twilio.type.PhoneNumber(destination), new com.twilio.type.PhoneNumber("+13158126244"), message).create();
            log.warn("A notification by SMS has been done. Destination {}, Message {}", destination, message);
        } catch (Exception e) {
            log.error("An error occurred while trying to notify by SMS. Destination {}, Message {}", destination, message, e);
        }
    }
}
