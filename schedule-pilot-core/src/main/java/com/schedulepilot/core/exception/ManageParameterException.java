package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class ManageParameterException extends SchedulePilotException {

    public ManageParameterException(ExceptionCode error, String message) {
        super(error, message);
    }
}
