package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "penalty_check_out")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyCheckOut extends BaseEntity {

    @Id
    @GeneratedValue(generator = "penalty_check_out_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "penalty_check_out_sequence_key_id",
            sequenceName = "penalty_check_out_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_check_out_id_fk", nullable = false)
    private TicketCheckOutEntity ticketCheckOutEntity;

    @Column(nullable = false, name = "price_penalty")
    private Long pricePenalty;
}
