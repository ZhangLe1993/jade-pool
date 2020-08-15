package com.biubiu.agent.enums;

/**
 *
 */
public enum Host {

    /**
     * 华东1
     */
    EAST_CHINA_I("唐僧", "http://172.172.0.11:8080"),

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
