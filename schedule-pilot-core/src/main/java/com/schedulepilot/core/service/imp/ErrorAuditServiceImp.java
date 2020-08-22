package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.ErrorAuditDto;
import com.schedulepilot.core.repository.ErrorAuditRepository;
import com.schedulepilot.core.service.ErrorAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ErrorAuditServiceImp implements ErrorAuditService {

    @Autowired
    private ErrorAuditRepository errorAuditRepository;

    @Override
    @Async
    @Transactional
    public void saveCommonError(ErrorAuditDto errorAuditDto) {
        errorAuditDto.setIsActive(true);
        this.errorAuditRepository.save(ErrorAuditService.convertDTOToEntity(errorAuditDto));
    }
}
