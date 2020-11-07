package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rol_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolAccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "rol_account_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "rol_account_sequence_key_id",
            sequenceName = "rol_account_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
