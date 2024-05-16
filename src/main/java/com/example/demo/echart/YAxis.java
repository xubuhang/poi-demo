/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;

import lombok.Data;

@Data
public class YAxis {

    private String type;

    public YAxis(String type) {
        this.type = type;
    }
}