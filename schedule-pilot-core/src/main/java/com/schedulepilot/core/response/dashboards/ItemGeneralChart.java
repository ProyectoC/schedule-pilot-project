package com.schedulepilot.core.response.dashboards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemGeneralChart implements Serializable {

    private String itemName;
    private List<BigDecimal> itemValues;

    public ItemGeneralChart(String itemName) {
        this.itemName = itemName;
        this.itemValues = new ArrayList<>();
    }

    public void addItem(BigDecimal item) {
        this.itemValues.add(item);
    }
}
