package com.flood_web.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.StartOutboundVoiceContactRequest;

import java.util.Map;

@Component
public class CallStrategy implements NotifyStrategy{

    @Autowired
    private ConnectClient connectClient;

    @Override
    public void notifyEvent(String destination, String message) {

        StartOutboundVoiceContactRequest callRequest = StartOutboundVoiceContactRequest.builder()
                .instanceId("012e71cd-70db-42ef-840a-3cdb3665ee3f")
                .contactFlowId("0825ced0-c31d-4446-8051-73e67fa603bf")
                .queueId("b63adb25-cf85-4de2-b29a-59836a8d9feb")
                .destinationPhoneNumber(destination)
                .attributes(Map.of("callTemplate", message)) // Pass dynamic message
                .build();

        connectClient.startOutboundVoiceContact(callRequest);

    }
}
