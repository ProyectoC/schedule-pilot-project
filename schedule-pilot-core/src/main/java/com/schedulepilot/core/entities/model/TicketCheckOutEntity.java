package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket_check_out")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCheckOutEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "ticket_check_out_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ticket_check_out_sequence_key_id",
            sequenceName = "ticket_check_out_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "track_id", unique = true)
    private String trackId;

    @ManyToOne
    @JoinColumn(name = "ticket_check_in_id_fk", nullable = false)
    private TicketCheckInEntity ticketCheckInEntity;

    @ManyToOne
    @JoinColumn(name = "ticket_check_status_id_fk", nullable = false)
    private TicketCheckStatusEntity ticketCheckStatusEntity;

    @ManyToOne
    @JoinColumn(name = "user_account_id_fk", nullable = false)
    private UserAccountEntity userAccountEntity;
}
