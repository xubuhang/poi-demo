package com.example.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.ByteUtils;
import com.deepoove.poi.xwpf.NumFormat;
import com.example.demo.echart.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class PoitlServiceImpl {
    private static final String TEMPLATE_REPORT_PATH = "static/template/report.docx";
    private static final String TEMPLATE_REPORT_SUB_PATH = "static/template/report_sub.docx";



    public void generateWord() {
        System.out.println("生成word文档");
        File templateFile = null;
        File subTemplateFile = null;
        try {
            templateFile = new ClassPathResource(TEMPLATE_REPORT_PATH).getFile();
            subTemplateFile = new ClassPathResource(TEMPLATE_REPORT_SUB_PATH).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("title", Texts.of("Sayi").color("000000").fontSize(30).create());
        data.put("name", "Sayi");
        data.put("author", Texts.of("Sayi").color("000000").create());
        data.put("link", Texts.of("website").link("http://deepoove.com").create());
        data.put("anchor", Texts.of("anchortxt").anchor("appendix1").create());

        // 图片流
        PictureRenderData pictureRenderData=Pictures.ofLocal("D:/down/java.jpg")
                .size(100, 120).create();
        data.put("image", pictureRenderData);

        RowRenderData row0 = Rows.of("列0", "列1", "列2").center().textColor("FFFFFF").bgColor("4472C4").create();
        RowRenderData row1 = Rows.create("列0", "列1", "列2");
        CellRenderData cell0 = Cells.of("Sayi").bgColor("FF0000").create();
        CellRenderData cell1 = Cells.of(pictureRenderData).bgColor("FF0000").create();

        RowRenderData row2 = Rows.of().addCell(cell0).addCell(cell1).addCell(cell0).create();
        MergeCellRule rule = MergeCellRule.builder().build();
        data.put("table0", Tables.of(row0, row1,row2).mergeRule(rule).create());

        ChartSingleSeriesRenderData pie = Charts
                .ofSingleSeries("饼图", new String[] { "美国", "中国" })
                .series("countries", new Integer[] { 9826675, 9596961 })
                .create();

        data.put("pieChart", pie);

        ChartMultiSeriesRenderData comb1 = Charts
                .ofComboSeries("折线图", new String[] { "中文"
                        , "中文", "English", "中文", "English", "中文"
                })
                .addLineSeries("youngs", new Double[] { 1.0 ,223.0, null, 32.0, 89.0, 49.0 })
                .addLineSeries("NewLine", new Double[] { 3.0 ,123.0, 59.0, null, 59.0, 154.0})
                .create();

        data.put("combChart", comb1);

        //列表集合
        List<String> varList = Arrays.asList("序号1",
                "序号2");

        //自定义序号的样式为  a)  b)  c)
        NumberingFormat.Builder builder = NumberingFormat.
                builder("%{0})") //%{0}) 可以指定自己需要的样式
                .withNumFmt(NumFormat.DECIMAL);  //小写字母
        NumberingFormat numberingFormat = builder.build(0);
        Numberings.NumberingBuilder of = Numberings.of(numberingFormat);//a)  b)  c)
        //列表 数据赋值
        varList.forEach(s -> of.addItem(s));
        NumberingRenderData numberingRenderData = of.create();
        data.put("list", numberingRenderData);

        EchartOption echartOption = new EchartOption();
        echartOption.setHeight(400);
        echartOption.setWidth(600);
        Option option = new Option();
//        option.setTitle(new Title("测试"));
        option.setBackgroundColor("#ffffff");
        option.setXAxis(new XAxis("category", false, new String[]{"1", "2", "3", "4", "5"}));
        option.setYAxis(new YAxis("value"));
        List<Series> series = new ArrayList<>();
        series.add(new Series("line", "测试", new Double[]{1d, 2d, 3d, 4d, 5d}));
        option.setSeries(series);
        echartOption.setOption(option);
        byte[] byteArray = HutoolHttpUtil.getEchartImg("http://localhost:8989/?config",echartOption);

        PictureRenderData pictureRenderData1=Pictures.ofBytes(byteArray)
                .size(600, 500).create();

        Map map0   = new HashMap<>();
        map0.put("image", pictureRenderData);
        map0.put("table", Tables.of(row0, row1,row2).mergeRule(rule).create());
        map0.put("combChart", comb1);

        Map map1   = new HashMap<>();
        map1.put("image", pictureRenderData1);
        map1.put("table", Tables.of(row0, row1,row2).mergeRule(rule).create());
        map1.put("combChart", comb1);
        List<Object> subData = new ArrayList<>();
        subData.add(map0);
        subData.add(map1);

        data.put("nested", Includes.ofBytes(ByteUtils.getLocalByteArray(subTemplateFile)).setRenderModel(subData).create());

        data.put("person", subData);

        List goods = new ArrayList<>();
        Map good   = new HashMap<>();
        good.put("name", "苹果");
        good.put("price", "10");
        goods.add(good);
        goods.add(good);
        data.put("goods", goods);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();

        Configure config = Configure.builder()
                .bind("goods", policy)
                .bind("customChart",new CustomChartRenderPolicy()).build();

        XWPFTemplate template = XWPFTemplate.compile(templateFile,config).render(data);


        try {
        template.writeAndClose(new FileOutputStream("D:\\down\\output.docx"));
        }catch (Exception e){
            log.error("替换生成图表报错：{}",e.getMessage());
            e.printStackTrace();
        }
    }

}
