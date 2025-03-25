package com.flood_web.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;


@Component
public class WppStrategy implements NotifyStrategy{
    @Override
    public void notifyEvent(String destination, String message) {

        Message
                .creator(new com.twilio.type.PhoneNumber("whatsapp:" + destination),
                        new com.twilio.type.PhoneNumber("whatsapp:+13158126244"),
                        message)
                .setMediaUrl(Arrays.asList(URI.create("https://www.portalambiental.com.mx/sites/default/files/styles/inline_content/public/media/image/2022/08/220808_zurich_protege_tu_hogar_ante_lluvias_e_inundaciones_2.png")))
                .create();
    }
}
