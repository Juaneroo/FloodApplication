package com.flood_web.service.notification;

interface NotifyStrategy {

    void notifyEvent(String destination, String message);
}
