package com.schedulepilot.core.commands.base;

public interface CommandExecutor<C extends Command, R extends CommandResult> {
    R execute(C command);
}
