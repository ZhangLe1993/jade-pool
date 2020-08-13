package com.biubiu.agent.controller;

import java.util.ArrayList;
import java.util.Map;

import com.biubiu.agent.annotation.SystemLog;
import com.biubiu.agent.dto.AgentDTO;
import com.biubiu.agent.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "agent")
public class AgentController {

    private final static Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AgentService agentService;

    @SystemLog(description = "获取代理列表")
    @GetMapping(value = "list")
    public ResponseEntity<?> getAgentList(@RequestParam(value = "keyWord", required = false) String keyWord,
                                          @RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "pageNo") int pageNo,
                                          @RequestParam(value = "pageSize") int pageSize) {
        try {
            Map<String, Object> map = agentService.getAgentList(keyWord, status, pageNo, pageSize);
            return new ResponseEntity<>(map,HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SystemLog(description = "创建代理")
    @PostMapping(value = "")
    public ResponseEntity<?> addAgent(@RequestBody AgentDTO agentDTO) {
        try {
            boolean success = agentService.createAgent(agentDTO);
            if(success) {
                return new ResponseEntity<>("创建成功",HttpStatus.OK);
            }
            return new ResponseEntity<>("已经存在或者重复创建",HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>("系统异常",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SystemLog(description = "修改代理")
    @PutMapping(value = "")
    public ResponseEntity<?> updateAgent(@RequestBody AgentDTO agentDTO) {
        try {
            boolean success = agentService.updateAgent(agentDTO);
            if(success) {
                return new ResponseEntity<>("修改成功",HttpStatus.OK);
            }
            return new ResponseEntity<>("没有可修改的记录",HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>("系统异常",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
