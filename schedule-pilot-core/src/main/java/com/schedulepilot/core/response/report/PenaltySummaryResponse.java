package com.schedulepilot.core.response.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PenaltySummaryResponse implements Serializable {

    private String username;
    private LocalDateTime penaltyDate;
    private Long pricePenalty;
    private String itemName;
    private LocalDateTime createdDate;
    private LocalDateTime returnDate;
}
