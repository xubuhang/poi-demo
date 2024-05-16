/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;
import lombok.Data;

import java.util.List;

@Data
public class Legend {
    private List<String> data;

    public Legend(List<String> data) {
        this.data = data;
    }
}