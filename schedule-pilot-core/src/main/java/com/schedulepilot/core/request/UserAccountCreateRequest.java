package com.schedulepilot.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountCreateRequest implements Serializable {

    @NotNull(message = "password can not be null.")
    @NotBlank(message = "password can not be blank.")
    private String password;

    @NotNull(message = "firstname can not be null.")
    @NotBlank(message = "firstname can not be blank.")
    private String firstName;

    @NotNull(message = "lastname can not be null.")
    @NotBlank(message = "lastname can not be blank.")
    private String lastName;

    @NotNull(message = "identification can not be null.")
    @NotBlank(message = "identification can not be blank.")
    private String identification;

    @NotNull(message = "identificationCode can not be null.")
    @NotBlank(message = "identificationCode can not be blank.")
    private String identificationCode;

    @NotNull(message = "email can not be null.")
    @NotBlank(message = "email can not be blank.")
    @Email(message = "email is not valid email address.")
    private String email;

    @NotNull(message = "emailBackup can not be null.")
    @NotBlank(message = "emailBackup can not be blank.")
    @Email(message = "emailBackup is not valid email address.")
    private String emailBackup;

    @NotNull(message = "phoneNumber can not be null.")
    @NotBlank(message = "phoneNumber can not be blank.")
    private String phoneNumber;

    @NotNull(message = "phoneCountryCode can not be null.")
    @NotBlank(message = "phoneCountryCode can not be blank.")
    private String phoneCountryCode;

    @NotNull(message = "rolAccount can not be null.")
    @JsonProperty("rolAccount")
    @Valid
    private RolAccountRequest rolAccountEntity;

    @JsonProperty("collegeCareer")
    @Valid
    private CollegeCareerRequest collegeCareerEntity;
}
