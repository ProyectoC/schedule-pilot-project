package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "token_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenTypeEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "token_type_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "token_type_sequence_key_id",
            sequenceName = "token_type_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
