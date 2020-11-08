package com.schedulepilot.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckInRequest implements Serializable {

    @NotNull(message = "userAccountId id can not be null.")
    @Positive(message = "userAccountId id can not be a negative number")
    private Long userAccountId;

    @Valid
    private List<CheckInProductRequest> checkInProductRequest;
}
