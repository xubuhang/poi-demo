package com.example.demo.echart;

import lombok.Data;

@Data
public class AxisLabel {
    private Integer interval=0;
    private Integer rotate=45;//倾斜度 -90 至 90 默认为0
    private Integer margin;

    private TextStyle  textStyle ;


    @Data
    class  TextStyle {
        private String fontWeight;
        private String color;
    }
}
