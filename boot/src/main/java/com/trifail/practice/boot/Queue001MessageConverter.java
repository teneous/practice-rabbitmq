//package com.trifail.practice.boot;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.support.converter.MessageConversionException;
//import org.springframework.amqp.support.converter.MessageConverter;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class Queue001MessageConverter implements MessageConverter {
//
//    @Override
//    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
//        File file = (File) object;
//        try {
//            FileReader fileReader = new FileReader(file);
//            char[] str = new char[1024];
//            int len = 0;
//            while ((len = fileReader.read(str)) != -1) {
//                String output = new String(str, 0, len);
//                System.out.println(output + "已经接受");
//                return new Message(output.getBytes(), messageProperties);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public Object fromMessage(Message message) throws MessageConversionException {
//        File file = new File("a.txt");
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter(file);
//            fileWriter.write(new String(message.getBody()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//}
