package com.biubiu.agent.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Agent {

    private Long id;

    private String localUrl;

    private String targetHost;

    private String targetUrl;

    private String headerAppend;

    private String cookieAppend;

    private String urlAppend;

    private String bodyAppend;

    private String status;

    private String createTime;

    private String updateTime;
}
