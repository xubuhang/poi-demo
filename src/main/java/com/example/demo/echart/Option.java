/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Option {
    String backgroundColor;
    private Title title;
    private Tooltip tooltip;
    private Legend legend;
    private Grid grid;
    private Toolbox toolbox;
    @JsonProperty("xAxis")
    private XAxis xAxis;
    @JsonProperty("yAxis")
    private YAxis yAxis;
    private List<Series> series;

    public Option() {
    }

    public Option(Title title, Legend legend, XAxis xAxis, YAxis yAxis, List<Series> series) {
        this.title = title;
        this.legend = legend;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.series = series;
    }

}