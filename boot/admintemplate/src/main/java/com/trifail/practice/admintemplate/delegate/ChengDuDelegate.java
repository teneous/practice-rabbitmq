package com.trifail.practice.admintemplate.delegate;


import com.trifail.practice.admintemplate.domain.City;

import java.io.File;
import java.util.Map;

public class  ChengDuDelegate {


    /*字节*/
    public void messageHandler(byte[] messageBody) {
        System.err.println("字节监听处理");
    }

    /*字符串*/
    public void messageHandler(String messageBody) {
        System.err.println("字符串监听处理");
    }

    /*json*/
    public void messageHandler(Map messageBody) {
        System.err.println("Json监听处理");
    }

    /*file*/
    public void messageHandler(File file) {
        System.err.println("File监听处理");
    }

    /*实体*/
    public void messageHandler(City city) {
        System.err.println("实体监听处理:" + city.toString());
    }
}
