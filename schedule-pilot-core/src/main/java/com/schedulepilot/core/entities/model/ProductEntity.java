package com.schedulepilot.core.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.ProductRolId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "product_sequence_key_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "product_sequence_key_id",
            sequenceName = "product_sequence_key_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "observations")
    private String observations;

    @ManyToOne
    @JoinColumn(name = "product_status_id_fk", nullable = false)
    private ProductStatusEntity productStatusEntity;

    @OneToMany(mappedBy = "productRolId.productEntity",
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<ProductRolEntity> productRolEntityList;

    public List<ProductRolEntity> getProductRolEntityList() {
        if (productRolEntityList != null) {
            List<ProductRolEntity> result = new ArrayList<>();
            for (ProductRolEntity productRol : productRolEntityList) {
                ProductRolId productRolId = new ProductRolId(productRol.getProductRolId().getRolAccountEntity());
                result.add(new ProductRolEntity(productRolId, productRol.getLoanTime()));
            }
            return result;
        } else {
            return null;
        }
    }

    public void addProductRole(ProductRolEntity productRolEntity) {
        this.productRolEntityList.add(productRolEntity);
        productRolEntity.getProductRolId().setProductEntity(this);
    }
}
