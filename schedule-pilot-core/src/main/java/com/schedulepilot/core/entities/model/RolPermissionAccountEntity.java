package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rol_permission_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolPermissionAccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "rol_permission_account_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "rol_permission_account_sequence_key_id",
            sequenceName = "rol_permission_account_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_account_id_fk", nullable = false)
    private RolAccountEntity rolAccountEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_account_id_fk", nullable = false)
    private PermissionAccountEntity permissionAccountEntity;
}
