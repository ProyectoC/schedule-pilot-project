package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class ManageTokenException extends SchedulePilotException {

    public ManageTokenException(ExceptionCode error, String message) {
        super(error, message);
    }
}
