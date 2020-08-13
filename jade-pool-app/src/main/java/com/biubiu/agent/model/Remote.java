package com.biubiu.agent.model;

import lombok.Data;

@Data
public class Remote {
    private String user = "root";
    private String host = "121.196.27.184";
    private int port = 22;
    private String password = "cxf.7057621";
    private String identity = "~/.ssh/id_rsa";
    private String passphrase = "";
}
