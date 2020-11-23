package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ProductRequestStatusDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCheckInResponse implements Serializable {

    private String productName;
    private String trackId;
    private LocalDateTime loanDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="America/Bogota")
    private Date createdDate;
    private int attempts;
    @JsonProperty("productRequestStatus")
    private ProductRequestStatusDto productRequestStatusEntity;
}
