package com.schedulepilot.core.commands.validateusertask;

import com.schedulepilot.core.commands.base.CommandExecutor;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.util.dto.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidateUserCommandExecutor implements CommandExecutor<ValidateUserCommand, ValidateUserCommandResult> {

    private final UserAccountService userAccountService;

    @Override
    public ValidateUserCommandResult execute(ValidateUserCommand command) {
        UserAccountDto user = command.getUserAccountDto();
        Validator validator = this.userAccountService.validationBeforeSave(user);
        return new ValidateUserCommandResult(validator);
    }
}
