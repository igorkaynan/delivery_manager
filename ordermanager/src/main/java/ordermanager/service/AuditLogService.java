package ordermanager.service;

import ordermanager.entity.AuditLog;
import ordermanager.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void saveLog(String action, String description) {

        AuditLog log = new AuditLog(
                action,
                description,
                LocalDateTime.now()
        );

        auditLogRepository.save(log);
    }
}