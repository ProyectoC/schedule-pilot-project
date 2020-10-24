package com.schedulepilot.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.dto.model.ProductTypeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
    private String serial1;
    private String observations;
    @JsonProperty("productStatus")
    private ProductStatusDto productStatusEntity;
    @JsonProperty("productType")
    private ProductTypeDto productTypeEntity;
}
