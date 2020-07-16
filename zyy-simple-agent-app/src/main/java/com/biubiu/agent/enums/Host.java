package com.biubiu.agent.enums;

/**
 *
 */
public enum Host {

    /**
     * 华东1
     */
    EAST_CHINA_I("华东1", "http://47.99.39.195:32761"),

    ;

    private String value;

    private String key;

    Host(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
