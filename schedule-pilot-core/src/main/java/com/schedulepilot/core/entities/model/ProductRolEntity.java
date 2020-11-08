package com.schedulepilot.core.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.schedulepilot.core.entities.BaseEntity;
import com.schedulepilot.core.entities.id.ProductRolId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "product_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductRolEntity extends BaseEntity implements Serializable {

    @EmbeddedId
    private ProductRolId productRolId;

    @Column(nullable = false, name = "loan_time")
    private Long loanTime;
}
