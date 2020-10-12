package com.schedulepilot.core.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.schedulepilot.core.dto.BaseDto;
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
public class ProductDto extends BaseDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String serial1;
    private String serial2;
    private String serial3;
    private String serial4;
    private String serial5;
    private String observations;
    private ProductStatusDto productStatusEntity;
    private ProductTypeDto productTypeEntity;
}
