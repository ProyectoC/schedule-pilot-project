package com.schedulepilot.core.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schedulepilot.core.dto.BaseDto;
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
public class ItemDto extends BaseDto implements Serializable {

    private Long id;
    private String name;
    private String serial1;
    @JsonProperty("itemStatus")
    private ItemStatusDto itemStatusEntity;
    @JsonProperty("product")
    private ProductDto productEntity;
    @JsonProperty("itemDetails")
    private List<ItemDetailDto> itemDetailEntityList;
}
