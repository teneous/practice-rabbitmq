package com.trifail.practice.admintemplate.utils;

public class StringUtils {


    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static boolean isNotEmpty(String source){
        return !isEmpty(source);
    }
}
