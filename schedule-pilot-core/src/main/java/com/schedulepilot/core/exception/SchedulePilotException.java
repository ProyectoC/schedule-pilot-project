package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class SchedulePilotException extends Exception {

    private ExceptionCode error;
    private String message;

    public SchedulePilotException(ExceptionCode error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public SchedulePilotException(ExceptionCode error, String message) {
        super(message);
        this.error = error;
        this.message = message;
    }

    public SchedulePilotException(ExceptionCode error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public SchedulePilotException(Throwable cause) {
        super(cause);
    }

    public SchedulePilotException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
