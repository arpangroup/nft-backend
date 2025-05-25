package com.arpangroup.user_service.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private final Long userId;

    public UserRegisteredEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

}
