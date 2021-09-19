package com.schedulepilot.core.commands.validateusertask;

import com.schedulepilot.core.commands.base.Command;
import com.schedulepilot.core.dto.model.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateUserCommand extends Command<ValidateUserCommandResult> {
    private final UserAccountDto userAccountDto;
}
