package com.arpangroup.referral_service.aspect;

import com.arpangroup.referral_service.annotation.Audit;
import com.arpangroup.referral_service.domain.entity.AuditLog;
import com.arpangroup.referral_service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditLogRepository auditLogRepository;
    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(audit)")
    public Object logAudit(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        String action = audit.action();
        String methodName = joinPoint.getSignature().toShortString();
        String username = getCurrentUsername(); // from SecurityContext


        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setMethod(methodName);
        auditLog.setUsername(username);
        auditLog.setTimestamp(LocalDateTime.now());

        // Example: You could log to DB or send to Kafka here
        log.info("Audit START - Action: {}, Method: {}", action, methodName);

        try {
            Object result = joinPoint.proceed();
            auditLog.setStatus("SUCCESS");
            auditLogRepository.save(auditLog);
            log.info("Audit SUCCESS - Action: {}", action);
            return result;
        } catch (Throwable ex) {
            log.error("Audit FAILED - Action: {}, Error: {}", action, ex.getMessage());
            auditLog.setStatus("FAILURE");
            auditLog.setErrorMessage(ex.getMessage());
            auditLogRepository.save(auditLog);
            throw ex;
        }
    }

    private String getCurrentUsername() {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";*/
        return "";
    }

}
