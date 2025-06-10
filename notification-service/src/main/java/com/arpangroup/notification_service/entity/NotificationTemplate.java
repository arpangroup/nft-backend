package com.arpangroup.notification_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notification_template")
@Data
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // unique template code
    private String type; // email, sms, push
    private String subject;
    @Lob
    private String content; // use {{var}} placeholders
}
