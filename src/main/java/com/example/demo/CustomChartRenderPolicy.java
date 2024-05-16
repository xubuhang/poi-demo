package com.example.demo;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.text.XDDFTextBody;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDispBlanksAs;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs;

import java.io.FileOutputStream;
import java.io.IOException;

public class CustomChartRenderPolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected void afterRender(RenderContext<Object> context) {
        //清空标签
        clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        XWPFRun run = context.getRun();
        NiceXWPFDocument xwpfDocument = context.getXWPFDocument();
        //在标签位置创建chart图表对象
        XWPFChart chart = xwpfDocument.createChart(run, 15 * Units.EMU_PER_CENTIMETER, 10 * Units.EMU_PER_CENTIMETER);
        //图表相关设置
        //图表标题
        chart.setTitleText("折线图例子");
        //图例是否覆盖标题
        chart.setTitleOverlay(false);

//        //图例设置
//        XDDFChartLegend legend = chart.getOrAddLegend();
//        //图例位置:上下左右
//        legend.setPosition(LegendPosition.TOP);

        //X轴(分类轴)相关设置
        //创建X轴,并且指定位置
        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        //x轴标题
        xAxis.setTitle("日期");

        //Y轴(值轴)相关设置
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT); // 创建Y轴,指定位置
        yAxis.setTitle("数值"); // Y轴标题


        //创建折线图对象
        XDDFLineChartData lineChart = (XDDFLineChartData) chart.createData(ChartTypes.LINE, xAxis, yAxis);

        String[] xAxisData = new String[10];
        for (int i = 0; i < 10; i++) {
            if(i==4||i==7) {
                xAxisData[i] = "null";
            }else {
                xAxisData[i] = "字典" + ((int) (Math.random() * 10));
            }
        }


        //设置X轴数据
        XDDFCategoryDataSource xAxisSource = XDDFDataSourcesFactory.fromArray(xAxisData);

        //设置Y轴数据
        Integer[] yAxisData1 = new Integer[10];
        for (int i = 0; i < 10; i++) {
            if(i==4){
                yAxisData1[i] = null;
            }else {
                yAxisData1[i] = (int) (Math.random() * 100);
            }

        }
        Integer[] yAxisData2 = new Integer[10];
        for (int i = 0; i < 10; i++) {
            if(i==7){
                yAxisData2[i] = null;
            }else {
                yAxisData2[i] = (int) (Math.random() * 100);
            }
        }
        XDDFNumericalDataSource<Integer> yAxisSource = XDDFDataSourcesFactory.fromArray(yAxisData1);
        XDDFNumericalDataSource<Integer> yAxisSource1 = XDDFDataSourcesFactory.fromArray(yAxisData2);


//        //加载折线图数据集
        XDDFLineChartData.Series lineSeries = (XDDFLineChartData.Series) lineChart.addSeries(xAxisSource, yAxisSource);
        lineSeries.setTitle("折线1", null);
        lineSeries.clearDataPoint(4);
        XDDFLineChartData.Series lineSeries1 = (XDDFLineChartData.Series) lineChart.addSeries(xAxisSource, yAxisSource1);
        lineSeries1.setTitle("折线2", null);
        lineSeries.clearDataPoint(7);

//        //线条样式:true平滑曲线,false折线
//        lineSeries.setSmooth(false);
//        // 标记点样式
//        lineSeries.setMarkerStyle(MarkerStyle.CIRCLE);
//        lineSeries.setMarkerSize((short) 5);
//        lineSeries.setLineProperties(new XDDFLineProperties(new XDDFSolidFillProperties(XDDFColor.from(PresetColor.RED))));

        //在标签位置绘制折线图
        chart.plot(lineChart);
        // legend
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP);
        legend.setOverlay(false);

        // 空点跨越
        CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
        disp.setVal(STDispBlanksAs.GAP);
        chart.getCTChart().setDispBlanksAs(disp);


    }
}
