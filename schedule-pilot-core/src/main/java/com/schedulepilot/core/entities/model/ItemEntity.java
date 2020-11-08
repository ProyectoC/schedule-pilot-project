package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "item_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "item_sequence_key_id",
            sequenceName = "item_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

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

    @ManyToOne
    @JoinColumn(name = "item_status_id_fk", nullable = false)
    private ItemStatusEntity itemStatusEntity;

    @ManyToOne
    @JoinColumn(name = "product_id_fk", nullable = false)
    private ProductEntity productEntity;
}
