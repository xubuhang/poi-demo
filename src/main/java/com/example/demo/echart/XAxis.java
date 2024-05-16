/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;
import lombok.Data;

@Data
public class XAxis {

    private String type;
    private boolean boundaryGap;
    private String[] data;

    private AxisLabel axisLabel;

    public XAxis(String type, boolean boundaryGap, String[] data) {
        this.type = type;
        this.boundaryGap = boundaryGap;
        this.data = data;
    }
}