package com.flood_web.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.StartOutboundVoiceContactRequest;



import java.util.Map;

@Slf4j
@Component
public class CallStrategy implements NotifyStrategy{

    @Autowired
    private ConnectClient connectClient;

    @Override
    public void notifyEvent(String destination, String message) {
        Twilio.init("ACee31bdf233c34fae681ba3a65fc3ed1c", "e1397bb390fec76c19e569c07900b717");
        try{
            Call call = Call.creator(
                    new PhoneNumber(destination),
                    new PhoneNumber("+13158126244"),
                    new com.twilio.type.Twiml(message)
            ).create();
            log.warn("A notification by call has been done. Call information: Call SID {}, De" +
                    "stination {}, Message {}", call.getSid(), destination, message);
        }catch (Exception e) {
            log.error("An error occurred while trying to notify by call. Destination {}, Message {}", destination, message, e);
        }

    }
}
