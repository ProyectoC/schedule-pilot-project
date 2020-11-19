package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "itemEntity",
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<ItemDetailEntity> itemDetailEntityList;

    public ItemEntity(Long id, String name, String serial1, String serial2, String serial3, String serial4, String serial5,
                      ItemStatusEntity itemStatusEntity) {
        this.id = id;
        this.name = name;
        this.serial1 = serial1;
        this.serial2 = serial2;
        this.serial3 = serial3;
        this.serial4 = serial4;
        this.serial5 = serial5;
        this.itemStatusEntity = itemStatusEntity;
    }

    public List<ItemDetailEntity> getItemDetailEntityList() {
        if (itemDetailEntityList != null) {
            List<ItemDetailEntity> result = new ArrayList<>();
            for (ItemDetailEntity itemDetailEntity : itemDetailEntityList) {
                result.add(new ItemDetailEntity(itemDetailEntity.getId(), itemDetailEntity.getKey(), itemDetailEntity.getValue()));
            }
            return result;
        } else {
            return null;
        }
    }

    public void addItemDetail(ItemDetailEntity itemDetailEntity) {
        this.itemDetailEntityList.add(itemDetailEntity);
        itemDetailEntity.setItemEntity(this);
    }
}
