package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket_check_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCheckLogEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "ticket_check_log_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ticket_check_log_sequence_key_id",
            sequenceName = "ticket_check_log_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_check_out_id_fk", nullable = false)
    private TicketCheckOutEntity ticketCheckOutEntity;

    @ManyToOne
    @JoinColumn(name = "user_account_id_fk", nullable = false)
    private UserAccountEntity userAccountEntity;
}
