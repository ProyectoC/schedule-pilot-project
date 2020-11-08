package com.schedulepilot.core.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.BaseDto;
import com.schedulepilot.core.util.dto.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto extends BaseDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String observations;
    @JsonProperty("productStatus")
    private ProductStatusDto productStatusEntity;
    private List<ProductRolDto> productRoles;

    // Validations
    public Validator validationForDisableProduct() {
        Validator validator = new Validator();
        if (!this.getIsActive()) {
            validator.setValid(false);
            validator.addError("The product must be active for disable.");
            return validator;
        }
        validator.setValid(true);
        return validator;
    }

    public Validator validationForCreateProduct() {
        Validator validator = new Validator();
        for (ProductRolDto productRolDto : productRoles) {
            if (productRolDto.getLoanTime() <= 0) {
                validator.setValid(false);
                validator.addError("Rol load time are no valid: " + productRolDto.getLoanTime());
            }
        }
        validator.setValid(true);
        return validator;
    }

    public Validator validationForUpdateProduct() {
        Validator validator = new Validator();

        if (!this.getIsActive()) {
            validator.setValid(false);
            validator.addError("The product must be active for update.");
            return validator;
        }

        for (ProductRolDto productRolDto : productRoles) {
            if (productRolDto.getLoanTime() <= 0) {
                validator.setValid(false);
                validator.addError("Rol load time are no valid: " + productRolDto.getLoanTime());
            }
        }
        validator.setValid(true);
        return validator;
    }
}
