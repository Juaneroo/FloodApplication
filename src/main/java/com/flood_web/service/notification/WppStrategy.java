package com.flood_web.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;

@Slf4j
@Component
public class WppStrategy implements NotifyStrategy {
    
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
            Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + destination),
                new com.twilio.type.PhoneNumber("whatsapp:" + twilioPhoneNumber),
                message
            )
            .setMediaUrl(Arrays.asList(URI.create("https://www.portalambiental.com.mx/sites/default/files/styles/inline_content/public/media/image/2022/08/220808_zurich_protege_tu_hogar_ante_lluvias_e_inundaciones_2.png")))
            .create();
            
            log.info("WhatsApp notification sent to {}", destination);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp notification to {}", destination, e);
        }
    }
}
