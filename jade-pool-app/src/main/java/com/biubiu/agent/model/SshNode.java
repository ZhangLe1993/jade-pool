package com.biubiu.agent.model;

import lombok.Data;

import java.util.List;
@Data
public class SshNode {
    private Long id;
    private String name;
    private String icon;
    private String host;
    private String type;
    private List<SshNode> children;
}
