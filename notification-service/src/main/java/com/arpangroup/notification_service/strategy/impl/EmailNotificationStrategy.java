package com.arpangroup.notification_service.strategy.impl;

import com.arpangroup.notification_service.enums.NotificationType;
import com.arpangroup.notification_service.service.EmailService;
import com.arpangroup.notification_service.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {
    private final EmailService emailService;

    @Override
    public NotificationType getType() { return NotificationType.EMAIL; }

    @Override
    public void send(String recipient, String subject, String content) {
        // Simulate email sending logic
        System.out.printf("[EMAIL] To: %s | Subject: %s | Body: %s\n", recipient, subject, content);
        emailService.sendSimpleMail(recipient, subject, content);
    }
}
