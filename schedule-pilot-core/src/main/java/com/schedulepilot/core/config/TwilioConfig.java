package com.schedulepilot.core.config;

import com.schedulepilot.core.constants.ParameterConstants;
import com.schedulepilot.core.service.ParameterService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "app.twilio.configuration")
@Data
@AllArgsConstructor
public class TwilioConfig {

    private final Account account = new Account();

    private final ParameterService parameterService;

    public String getAuthToken() {
        return this.parameterService.getByNameNull(ParameterConstants.PARAMETER_TWILIO_CONFIGURATION_AUTH_TOKEN).getValue();
    }

    @Data
    public static class Account {

        @NotNull(message = "app.twilio.configuration.account.sid can not be null")
        @NotBlank(message = "app.twilio.configuration.account.sid can not be empty")
        private String sid;

        @NotNull(message = "app.twilio.configuration.account.number can not be null")
        @NotBlank(message = "app.twilio.configuration.account.number can not be empty")
        private String number;
    }
}
