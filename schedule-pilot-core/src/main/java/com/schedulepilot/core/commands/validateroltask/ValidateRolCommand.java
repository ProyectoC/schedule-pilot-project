package com.schedulepilot.core.commands.validateroltask;

import com.schedulepilot.core.commands.base.Command;
import com.schedulepilot.core.dto.model.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateRolCommand extends Command<ValidateRolCommandResult> {
    private final UserAccountDto userAccountDto;
}
