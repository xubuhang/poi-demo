package com.example.demo;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.echart.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HutoolHttpUtil {

    public static JSONObject get(String url, Map<String, Object> queryParams) throws IOException {
        return get(url, queryParams, new HashMap<>(1));
    }

    public static JSONObject get(String url, Map<String, Object> queryParams, Map<String, String> headers) throws IOException {
        String body = HttpRequest.get(url).form(queryParams).addHeaders(headers).execute().body();
        return JSONObject.parseObject(body);
    }

    public static JSONObject post(String url, String json, Map<String, String> headers) {
        String body = HttpRequest.post(url).body(json).addHeaders(headers).execute().body();
        return JSONObject.parseObject(body);
    }

    public  static InputStream downloadGet(String fileUrl) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = HttpRequest.get(fileUrl).execute().bodyStream();
            System.out.println("文件下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    public  static InputStream downloadPost(String url, String json, Map<String, String> headers)  {
        InputStream inputStream = null;
        try {
            inputStream = HttpRequest.post(url).body(json).addHeaders(headers).execute().bodyStream();
            System.out.println("文件下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    public static void main(String[] args) {
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
        byte[] byteArray = getEchartImg("http://localhost:8989/?config",echartOption);
    }

    public static byte[]  getEchartImg(String url,EchartOption echartOption) {
        String json = JSON.toJSONString(echartOption);
        InputStream inputStream  = downloadPost(url, json, new HashMap<>(1));
        byte[]  byteArray  = inputStream2Bytes(inputStream);
        return byteArray;
    }

    private static byte[] inputStream2Bytes (InputStream inputStream) {
        if (inputStream == null) {
            System.out.println("inputStream is null");
            return null;
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            byte[] byteArray = outputStream.toByteArray();

            // Do something with the byte array

            inputStream.close();
            outputStream.close();

            System.out.println("Input stream has been successfully converted to a byte array.");
            return byteArray;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
    }

    private static void saveFile(InputStream inputStream, String path) {
        if (inputStream == null) {
            System.out.println("inputStream is null");
            return;
        }
        //保存文件
        try {
            OutputStream outputStream = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("Input stream has been successfully converted to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
