package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "permission_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionAccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "permission_account_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "permission_account_sequence_key_id",
            sequenceName = "permission_account_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
