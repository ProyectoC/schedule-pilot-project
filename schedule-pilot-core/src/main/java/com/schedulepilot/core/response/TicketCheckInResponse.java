package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.dto.model.TicketCheckStatusDto;
import com.schedulepilot.core.entities.model.ItemEntity;
import com.schedulepilot.core.entities.model.RequestCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketCheckInResponse implements Serializable {

    private Long id;
    private String trackIdTicket;
    private String trackIdRequestOrigin;
    @JsonProperty("item")
    private ItemDto itemEntity;
    @JsonProperty("ticketCheckStatus")
    private TicketCheckStatusDto ticketCheckStatusEntity;
    private LocalDateTime deliveryDate;
    private LocalDateTime returnDate;
}
