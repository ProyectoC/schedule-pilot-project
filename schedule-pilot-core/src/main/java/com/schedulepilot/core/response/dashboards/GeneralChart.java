package com.schedulepilot.core.response.dashboards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralChart implements Serializable {

    private String title;
    private String subTitle;
    private String type;
    private String xName;
    private String yName;
    private List<ItemGeneralChart> listItems;
    private List<String> labels;

    public GeneralChart(String title, String subTitle, String type, String xName, String yName) {
        this.title = title;
        this.subTitle = subTitle;
        this.type = type;
        this.xName = xName;
        this.yName = yName;
        this.listItems = new ArrayList<>();
        this.labels = new ArrayList<>();
    }

    public void addItemGeneral(ItemGeneralChart item) {
        this.listItems.add(item);
    }

    public void addLabel(String label) {
        this.labels.add(label);
    }
}
