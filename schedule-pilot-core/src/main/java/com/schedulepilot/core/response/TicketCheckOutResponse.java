package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.dto.model.TicketCheckStatusDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketCheckOutResponse implements Serializable {

    private Long id;
    private String trackIdTicketOut;
    private String trackIdTicketIn;
    @JsonProperty("item")
    private ItemDto itemEntity;
    @JsonProperty("ticketCheckStatus")
    private TicketCheckStatusDto ticketCheckStatusEntity;
    private LocalDateTime deliveryDate;
    private LocalDateTime returnDate;
}
