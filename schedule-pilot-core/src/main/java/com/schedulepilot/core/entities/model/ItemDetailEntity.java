package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_detail", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"key", "item_id_fk"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "item_detail_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "item_detail_sequence_key_id",
            sequenceName = "item_detail_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "key")
    private String key;

    @Column(nullable = false, name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id_fk", nullable = false)
    private ItemEntity itemEntity;

    public ItemDetailEntity(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }
}
