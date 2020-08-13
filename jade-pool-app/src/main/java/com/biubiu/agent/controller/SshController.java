package com.biubiu.agent.controller;

import com.biubiu.agent.annotation.SystemLog;
import com.biubiu.agent.model.SshNode;
import com.biubiu.agent.service.SshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "ssh")
public class SshController {

    private final static Logger logger = LoggerFactory.getLogger(SshController.class);

    @Autowired
    private SshService sshService;

    @SystemLog(description = "获取远程连接树")
    @GetMapping(value = "tree")
    public ResponseEntity<?> getSshTree() {
        try {
            List<SshNode> tree = sshService.getSshTree();
            return new ResponseEntity<>(tree, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
