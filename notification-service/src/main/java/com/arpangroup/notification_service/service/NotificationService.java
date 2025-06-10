package com.arpangroup.notification_service.service;

import com.arpangroup.notification_service.dto.NotificationRequest;
import com.arpangroup.notification_service.entity.NotificationTemplate;
import com.arpangroup.notification_service.repository.NotificationTemplateRepository;
import com.arpangroup.notification_service.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationTemplateRepository templateRepo;
    private final List<NotificationStrategy> strategies;

    public void sendNotification(NotificationRequest request) {
        NotificationTemplate template = templateRepo
                .findByCodeAndType(request.getTemplateCode(), request.getType().name())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        String subject = resolveTemplate(template.getSubject(), request.getProperties());
        String content = resolveTemplate(template.getContent(), request.getProperties());

        strategies.stream()
                .filter(s -> s.getType() == request.getType())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No strategy found"))
                .send(request.getRecipient(), subject, content);
    }

    private String resolveTemplate(String template, Map<String, String> props) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}
