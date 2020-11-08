package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class LoanProcessException extends SchedulePilotException {

    public LoanProcessException(ExceptionCode error, String message) {
        super(error, message);
    }
}
