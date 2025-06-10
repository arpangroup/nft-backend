package com.arpangroup.notification_service.strategy;

import com.arpangroup.notification_service.enums.NotificationType;

public interface NotificationStrategy {
    NotificationType getType();
    void send(String recipient, String subject, String content);
}
