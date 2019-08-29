package com.trifail.practice.boot;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Queue1Delegate {

    public void queue1MessageHandler(byte[] messageBody) {
        System.out.println("进入自定义消息体，msg:" + new String(messageBody, StandardCharsets.UTF_8));
    }


    /**
     * 如果传入的是.txt文件，从中读取数据到内存中
     *
     * @param file
     */
    public void queue1MessageHandler(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = fis.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
