package com.example.demo;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fax
 * @date 2023-08-23 11:08
 */
public class TestWord {
    private static final String TEMPLATE_REPORT_PATH = "static/template/template.docx";

    public static void main(String[] args) {
        File templateFile = null;
        try {
            templateFile = new ClassPathResource(TEMPLATE_REPORT_PATH).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = "今天是个好日子";

        Map<String, Object> params = new HashMap<>();
        params.put("title", "真不错");
        params.put("content", content);
        params.put("header1", "大家都开心");
        params.put("header2", "好的");
        params.put("header3", "How are you?");
        params.put("header4", "fine");
        params.put("header5", "thank you!");

        List<String[]> table1 = new ArrayList<>();
        table1.add(new String[]{"1", "11", "22", "33", "44"});
        table1.add(new String[]{"2", "55", "66", "77", "88"});
        table1.add(new String[]{"2", "99", "1010", "1111", "1212"});

        List<String[]> table2 = new ArrayList<>();
        table2.add(new String[]{"1", "1313", "1414", "1515"});
        table2.add(new String[]{"2", "1616", "1717", "1818"});
        table2.add(new String[]{"3", "1919", "2020", "2121"});
        //饼图数据
        String[] title = new String[]{"1", "2", "3", "4", "5"};
        Double[] value = new Double[]{70.34, 40.5, 54.9, 334.8, 546.2};
        // 柱状图、折线图 数据

        String[] quarter = new String[]{"第一年", "第二年", "第三年", "第四年"};
        Map<String, Number[]> map = new HashMap<>();
        map.put("姜文", new Integer[]{6688, 4399, 5327, 6379});
        map.put("周润发", new Integer[]{6799, 7499, 6429, 7379});
        map.put("葛优", new Integer[]{5899, 6599, 7665, 8573});
        System.out.println(System.getProperty("user.dir"));
        String outPath = "D:\\down\\word文件.docx";
        try (InputStream is = new FileInputStream(templateFile);
             OutputStream os = new FileOutputStream(outPath);
             XWPFDocument document = new XWPFDocument(OPCPackage.open(is))) {
            WordUtils wordUtils = new WordUtils();
//            // 替换段落中的参数
            wordUtils.replaceInPara(document, params);
//            // 替换表格中的参数
            wordUtils.replaceInTable(document, new int[]{0}, params);
//            // 给表格追加记录
            wordUtils.insertTableRow(document.getTableArray(0), table1);
            // 创建表格
            wordUtils.createTitle(document.createParagraph(), "二、表格二");

            wordUtils.createTable(document, table2);
            // 创建饼图
            wordUtils.createTitle(document.createParagraph(), "三、饼图");
            wordUtils.createPieChart(document, "豆瓣评分", title, value);

            wordUtils.createTitle(document.createParagraph(), "四、柱形图");
            // 创建柱状图
            wordUtils.createBarChart(document, "标题", null, null, quarter, map);
            wordUtils.createTitle(document.createParagraph(), "五、条形图");
            // 创建柱状图
            wordUtils.createLineChart(document, "标题", "季度", "数值", quarter, map);
            wordUtils.createTitle(document.createParagraph(), "六、混合图");
            // 创建柱状图
            wordUtils.createBarLineChart(document, "标题", null, null, quarter, map);

            document.write(os);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}