package com.schedulepilot.core.commands.validateroltask;

import com.schedulepilot.core.commands.base.CommandExecutor;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.util.dto.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidateRolCommandExecutor implements CommandExecutor<ValidateRolCommand, ValidateRolCommandResult> {

    private final GlobalListDinamicService globalListDinamicService;

    @Override
    public ValidateRolCommandResult execute(ValidateRolCommand command) {
        UserAccountDto user = command.getUserAccountDto();
        try {
            RolAccountDto rolAccount = this.globalListDinamicService.getRolAccountByIdOrException(user.getRolAccountEntity().getId());
            Validator validator = rolAccount.validationForCreateUser(user);
            return new ValidateRolCommandResult(validator);
        } catch (Exception ex) {
            Validator validator = new Validator();
            validator.setValid(false);
            validator.addError(ex.getMessage());
            return new ValidateRolCommandResult(validator);
        }
    }
}
