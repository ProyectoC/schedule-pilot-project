package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_check_in")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCheckInEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "ticket_check_in_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ticket_check_in_sequence_key_id",
            sequenceName = "ticket_check_in_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "track_id", unique = true)
    private String trackId;

    @ManyToOne
    @JoinColumn(name = "request_check_in_id_fk", nullable = false)
    private RequestCheckInEntity requestCheckInEntity;

    @ManyToOne
    @JoinColumn(name = "item_id_fk", nullable = false)
    private ItemEntity itemEntity;

    @ManyToOne
    @JoinColumn(name = "ticket_check_status_id_fk", nullable = false)
    private TicketCheckStatusEntity ticketCheckStatusEntity;

    @Column(nullable = false, name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(nullable = false, name = "return_date")
    private LocalDateTime returnDate;
}
