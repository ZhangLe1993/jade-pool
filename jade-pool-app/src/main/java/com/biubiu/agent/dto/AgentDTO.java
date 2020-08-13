package com.biubiu.agent.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDTO {
    private Long id;

    private String localUrl;

    private String targetHost;

    private String targetUrl;

    private List<Append> header;

    private List<Append> cookie;

    private List<Append> param;

    private List<Append> body;

    private String status;

    private String createTime;

    private String updateTime;
}
