package com.trifail.practice.admintemplate.domain;

public class City {

    private int level;
    private String number_code;
    private String desc;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNumber_code() {
        return number_code;
    }

    public void setNumber_code(String number_code) {
        this.number_code = number_code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "City{" +
                "level=" + level +
                ", number_code='" + number_code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
