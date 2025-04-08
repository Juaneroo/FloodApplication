package com.flood_web.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.StartOutboundVoiceContactRequest;

import java.util.Map;

@Slf4j
@Component
public class CallStrategy implements NotifyStrategy {

    @Autowired
    private ConnectClient connectClient;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.account.auth-token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Override
    public void notifyEvent(String destination, String message) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        try {
            Call call = Call.creator(
                    new PhoneNumber(destination),
                    new PhoneNumber(twilioPhoneNumber),
                    new com.twilio.type.Twiml(message)
            ).create();
            log.warn("A notification by call has been done. Call information: Call SID {}, Destination {}, Message {}",
                    call.getSid(), destination, message);
        } catch (Exception e) {
            log.error("An error occurred while trying to notify by call. Destination {}, Message {}", destination, message, e);
        }
    }
}
