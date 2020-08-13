package com.biubiu.agent.model;

import lombok.Data;

@Data
public class ConnectModel {
    private String loginName;
    private Integer port;
    private String host;
}
