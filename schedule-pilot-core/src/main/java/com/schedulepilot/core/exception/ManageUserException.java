package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class ManageUserException extends SchedulePilotException {
    
    public ManageUserException(ExceptionCode error, String message) {
        super(error, message);
    }
}
