package com.schedulepilot.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckLogRequest implements Serializable {

    @NotNull(message = "userAccountId can not be null.")
    @Positive(message = "userAccountId can not be a negative number")
    private Long userAccountId;

    @NotNull(message = "trackIdentificationCheckOut can not be null.")
    @NotBlank(message = "trackIdentificationCheckOut can not be empty.")
    private String trackIdentificationCheckOut;
}
