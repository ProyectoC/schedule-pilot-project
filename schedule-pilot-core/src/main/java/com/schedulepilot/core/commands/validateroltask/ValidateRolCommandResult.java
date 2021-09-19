package com.schedulepilot.core.commands.validateroltask;

import com.schedulepilot.core.commands.base.CommandResult;
import com.schedulepilot.core.util.dto.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateRolCommandResult extends CommandResult {
    private final Validator validator;
}
