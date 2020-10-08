package com.schedulepilot.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountChangePasswordRequest implements Serializable {

    @NotNull(message = "username can not be null.")
    @NotBlank(message = "username can not be blank.")
    private String username;

    @NotNull(message = "actualPassword can not be null.")
    @NotBlank(message = "actualPassword can not be blank.")
    private String actualPassword;

    @NotNull(message = "newPassword can not be null.")
    @NotBlank(message = "newPassword can not be blank.")
    private String newPassword;
}
