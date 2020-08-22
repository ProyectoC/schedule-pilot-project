package com.schedulepilot.core.email.service;

import com.schedulepilot.core.email.model.EmailDto;
import com.schedulepilot.core.email.model.EmailProperties;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(EmailProperties emailProperties, EmailDto emailDto) throws SchedulePilotException;
}
