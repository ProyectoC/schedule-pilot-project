package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.LogAuditDto;
import com.schedulepilot.core.repository.LogAuditRepository;
import com.schedulepilot.core.service.LogAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class LogAuditServiceImp implements LogAuditService {

    @Autowired
    private LogAuditRepository logAuditRepository;

    @Override
    @Async
    @Transactional
    public void saveCommonLog(LogAuditDto logAuditDto) {
        logAuditDto.setIsActive(true);
        this.logAuditRepository.save(LogAuditService.convertDTOToEntity(logAuditDto));
    }
}
