package com.schedulepilot.core.exception;

import lombok.Getter;

@Getter
public class ManageProductException extends SchedulePilotException {

    public ManageProductException(ExceptionCode error, String message) {
        super(error, message);
    }
}
