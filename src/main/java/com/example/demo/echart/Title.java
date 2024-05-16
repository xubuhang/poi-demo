/**
  * Copyright 2024 bejson.com 
  */
package com.example.demo.echart;

import lombok.Data;

@Data
public class Title {

    private String text;

    public Title(String text) {
        this.text = text;
    }
}