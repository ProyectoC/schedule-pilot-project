package com.schedulepilot.core.entities.model;

import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.ProductRolId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRolEntity extends BaseEntity implements Serializable {

    @EmbeddedId
    private ProductRolId productRolId;

    @Column(nullable = false, name = "days")
    private int days;
}
