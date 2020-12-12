package com.schedulepilot.core.message;

import org.springframework.stereotype.Service;

@Service
public interface MessageSenderService {

    void sendInformationMessage(String message, String phone);

    void sendWarningMessage(String message, String phone);
}
