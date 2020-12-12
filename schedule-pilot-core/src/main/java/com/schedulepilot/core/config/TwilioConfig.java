package com.schedulepilot.core.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "app.twilio.configuration")
@Data
public class TwilioConfig {

    private final Account account = new Account();

    @Getter
    @NotNull(message = "app.twilio.configuration.auth-token can not be null")
    @NotBlank(message = "app.twilio.configuration.auth-token can not be empty")
    private String authToken;

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
