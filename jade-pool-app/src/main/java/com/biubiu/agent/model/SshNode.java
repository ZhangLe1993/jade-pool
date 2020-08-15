package com.biubiu.agent.model;

import lombok.Data;

import java.util.List;
@Data
public class SshNode {
    private Long id;
    private String name;
    private String icon;
    private String host;
    private String user;
    private String password;
    private String port;
    private String type;
    private List<SshNode> children;
}
