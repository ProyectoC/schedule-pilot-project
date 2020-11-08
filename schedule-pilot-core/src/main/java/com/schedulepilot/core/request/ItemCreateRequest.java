package com.schedulepilot.core.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.model.ItemDetailDto;
import com.schedulepilot.core.dto.model.ItemStatusDto;
import com.schedulepilot.core.dto.model.ProductDto;
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
public class ItemCreateRequest implements Serializable {
    
    private String name;
    private String serial1;
    @JsonProperty("itemStatus")
    private ItemStatusDto itemStatusEntity;
    @JsonProperty("product")
    private ProductDto productEntity;
    @JsonProperty("itemDetails")
    private List<ItemDetailDto> itemDetailEntityList;
}
