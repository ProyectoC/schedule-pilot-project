package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class ManageRolException extends SchedulePilotException {

    public ManageRolException(ExceptionCode error, String message) {
        super(error, message);
    }
}
