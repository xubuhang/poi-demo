/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;
import lombok.Data;

@Data
public class Series {

    private String name;
    private String type;
    private String stack;
    private boolean showSymbol;

    private boolean smooth;
    private Double[] data;

    public Series(String type, String name,Double[] data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }
}