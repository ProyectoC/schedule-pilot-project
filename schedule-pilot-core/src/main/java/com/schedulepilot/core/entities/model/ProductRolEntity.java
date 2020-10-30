package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRolEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "item_status_sequence_key_id")
    @SequenceGenerator(
            name = "item_status_sequence_key_id",
            sequenceName = "item_status_sequence_key_id",
            initialValue = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;
}
