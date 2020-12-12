package com.schedulepilot.core.message;

import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void sendMessage(String phoneNumber, String message) throws SchedulePilotException;
}
