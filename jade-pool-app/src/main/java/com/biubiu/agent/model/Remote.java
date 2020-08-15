package com.biubiu.agent.model;

import lombok.Data;

@Data
public class Remote {
    private String user = "root";
    private String host = "172.172.0.11";
    private int port = 20001;
    private String password = "root";
    private String identity = "~/.ssh/id_rsa";
    private String passphrase = "";
}
