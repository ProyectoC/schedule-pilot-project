package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.CollegeCareerDto;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.UserNotificationDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountResponse implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String identification;
    private String identificationCode;
    private String email;
    private String emailBackup;
    @JsonProperty("rolAccount")
    private RolAccountDto rolAccountEntity;
    @JsonProperty("collegeCareer")
    private CollegeCareerDto collegeCareerEntity;
    private List<UserNotificationDto> notificationEntities;
}
