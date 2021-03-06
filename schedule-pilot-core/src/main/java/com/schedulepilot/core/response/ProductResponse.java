package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ProductRolDto;
import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.dto.model.ProductTypeDto;
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
public class ProductResponse implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String observations;
    @JsonProperty("productStatus")
    private ProductStatusDto productStatusEntity;
    private List<ProductRolDto> productRoles;
}
