package com.flood_web.service.notification;

/**
 * Strategy interface for implementing different notification methods.
 * This interface follows the Strategy design pattern to allow for flexible
 * notification implementations (e.g., email, SMS, push notifications).
 */
interface NotifyStrategy {

    /**
     * Sends a notification to the specified destination with the given message.
     *
     * @param destination The target address or identifier where the notification should be sent
     *                    (e.g., email address, phone number, device token)
     * @param message The content of the notification to be sent
     */
    void notifyEvent(String destination, String message);
}
