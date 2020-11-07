package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_request_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestStatusEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "product_request_status_sequence_key_id")
    @SequenceGenerator(
            name = "product_request_status_sequence_key_id",
            sequenceName = "product_request_status_sequence_key_id",
            initialValue = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "description")
    private String description;
}
