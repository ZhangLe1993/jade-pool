package com.biubiu.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Append {

    private String key;

    private String value;

    public Append() {
    }

    public Append(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
