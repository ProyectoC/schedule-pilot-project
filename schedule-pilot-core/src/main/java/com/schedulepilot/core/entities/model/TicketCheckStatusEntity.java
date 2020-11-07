package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket_check_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCheckStatusEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "ticket_check_status_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "ticket_check_status_sequence_key_id",
            sequenceName = "ticket_check_status_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
