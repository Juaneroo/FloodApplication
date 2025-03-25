package com.flood_web.service.risk;

public class MessageTemplate {


    public static final String TEMPLATE_1_CALL = "<Response>"
            + "<Pause length='0.5'/>"
            + "<Say>"
            + "Hola, <prosody rate='medium'>[name]</prosody>. "
            + "Actualmente, te encuentras en "
            + "<prosody volume='loud' rate='slow' pitch='+5%'>[risk]</prosody> "
            + "debido al afluente que pasa por tu zona. "
            + "Sigue el plan de acción de riesgo de tu comunidad."
            + "</Say>"
            + "</Response>";

    public static final String TEMPLATE_1_WPP = "Hola, [name]. Actualmente, te encuentras en [risk] debido al afluente que pasa por tu zona. " +
            "Sigue el plan de acción de riesgo de tu comunidad; ¡Abre la imagen para saber que hacer!";

    public static final String TEMPLATE_1_SMS = "Hola, [name]. Actualmente, te encuentras en [risk] debido al afluente que pasa por tu zona. " +
            "Sigue el plan de acción de riesgo de tu comunidad";

}
