package com.arpangroup.notification_service.dto;

import com.arpangroup.notification_service.enums.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class NotificationRequest {
    private NotificationType type;
    private String recipient;
    private String templateCode;
    private Map<String, String> properties;
}
