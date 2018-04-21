package com.gersion.superlock.bean;

/**
 * Created by gersy on 2018/3/31.
 */

public class ExtraOptionBean {
    private String key;
    private String value;

    public ExtraOptionBean() {
    }

    public ExtraOptionBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExtraOptionBean{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
