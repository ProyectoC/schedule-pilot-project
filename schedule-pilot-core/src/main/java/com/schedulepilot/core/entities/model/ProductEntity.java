package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "product_sequence_key_id")
    @SequenceGenerator(
            name = "product_sequence_key_id",
            sequenceName = "product_sequence_key_id",
            initialValue = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "serial_1")
    private String serial1;

    @Column(nullable = true, name = "serial_2")
    private String serial2;

    @Column(nullable = true, name = "serial_3")
    private String serial3;

    @Column(nullable = true, name = "serial_4")
    private String serial4;

    @Column(nullable = true, name = "serial_5")
    private String serial5;

    @Column(nullable = false, name = "observations")
    private String observations;

    @ManyToOne
    @JoinColumn(name = "product_status_id_fk", nullable = false)
    private ProductStatusEntity productStatusEntity;
}
