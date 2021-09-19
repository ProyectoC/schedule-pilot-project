package com.schedulepilot.core.commands.config;

import com.schedulepilot.core.commands.base.CommandDispatcher;
import com.schedulepilot.core.commands.base.CommandExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

@Service
public class SpringCommandDispatcher extends CommandDispatcher {

    private final Map<String, CommandExecutor> rawMap;

    public SpringCommandDispatcher(Map<String, CommandExecutor> rawMap) {
        this.rawMap = rawMap;
    }

    @PostConstruct
    private void setUp() {
        if (rawMap != null && !rawMap.isEmpty()) {
            for (CommandExecutor commandExecutor : rawMap.values()) {
                Type command = ((ParameterizedType) commandExecutor.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
                preparedMap.put((Class) command, commandExecutor);
            }
        }
    }
}
