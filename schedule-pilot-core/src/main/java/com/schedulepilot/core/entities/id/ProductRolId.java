package com.schedulepilot.core.entities.id;

import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Component
public class ProductRolId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id_fk")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id_fk")
    private RolAccountEntity rolAccountEntity;

    public ProductRolId(RolAccountEntity rolAccountEntity) {
        this.rolAccountEntity = rolAccountEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRolId that = (ProductRolId) o;
        return productEntity.equals(that.productEntity) &&
                rolAccountEntity.equals(that.rolAccountEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productEntity, rolAccountEntity);
    }
}
