package com.biubiu.agent.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Ssh {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String config;
    private Long parentId;
    private String createTime;
    private String createUk;
    private String updateTime;
    private String updateUk;
}
