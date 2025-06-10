package com.arpangroup.notification_service.service;

import com.arpangroup.notification_service.dto.EmailDetails;

public interface EmailService {
    // send a simple email
    void sendSimpleMail(String to, String subject, String text);

    // send an email with attachment
    void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment);
}
