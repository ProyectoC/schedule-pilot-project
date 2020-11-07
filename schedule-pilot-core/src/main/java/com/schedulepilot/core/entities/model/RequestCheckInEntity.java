package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "request_check_in")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckInEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "request_check_in_sequence_key_id")
    @SequenceGenerator(
            name = "request_check_in_sequence_key_id",
            sequenceName = "request_check_in_sequence_key_id",
            initialValue = 1
    )
    private Long id;

    @Column(nullable = false, name = "track_id")
    private String trackId;

    @ManyToOne
    @JoinColumn(name = "user_account_id_fk", nullable = false)
    private UserAccountEntity userAccountEntity;
}
