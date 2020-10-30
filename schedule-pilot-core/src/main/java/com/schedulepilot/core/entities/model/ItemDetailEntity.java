package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "item_detail_sequence_key_id")
    @SequenceGenerator(
            name = "item_detail_sequence_key_id",
            sequenceName = "item_detail_sequence_key_id",
            initialValue = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "item_id_fk", nullable = false)
    private ItemEntity itemEntity;
}
