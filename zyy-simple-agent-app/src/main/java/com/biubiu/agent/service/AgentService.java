package com.biubiu.agent.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import com.biubiu.agent.dao.AgentDao;
import com.biubiu.agent.dto.AgentDTO;
import com.biubiu.agent.dto.Append;
import com.biubiu.agent.pojo.Agent;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentService {

    @Resource
    private AgentDao agentDao;


    @Transactional(rollbackFor = Exception.class)
    public boolean createAgent(AgentDTO agentDTO) {
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDTO, agent);
        List<Append> header = agentDTO.getHeader();
        List<Append> cookie = agentDTO.getCookie();
        List<Append> param = agentDTO.getParam();
        List<Append> body = agentDTO.getBody();
        if(header != null && header.size() != 0) {
            header = header.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
        }
        if(cookie != null && cookie.size() != 0) {
            cookie = cookie.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
        }
        if(param != null && param.size() != 0) {
            param = param.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
        }
        if(body != null && body.size() != 0) {
            body = body.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
        }
        agent.setHeaderAppend(JSON.toJSONString(header));
        agent.setCookieAppend(JSON.toJSONString(cookie));
        agent.setUrlAppend(JSON.toJSONString(param));
        agent.setBodyAppend(JSON.toJSONString(body));
        int count = agentDao.insert(agent);
        return count > 0;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean updateAgent(AgentDTO agentDTO) {
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDTO, agent);
        List<Append> header = agentDTO.getHeader();
        List<Append> cookie = agentDTO.getCookie();
        List<Append> param = agentDTO.getParam();
        List<Append> body = agentDTO.getBody();
        if(header != null && header.size() != 0) {
            header = header.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
            if(header.size() != 0) {
                agent.setHeaderAppend(JSON.toJSONString(header));
            }
        }
        if(cookie != null && cookie.size() != 0) {
            cookie = cookie.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
            if(cookie.size() != 0) {
                agent.setCookieAppend(JSON.toJSONString(cookie));
            }
        }
        if(param != null && param.size() != 0) {
            param = param.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
            if(param.size() != 0) {
                agent.setUrlAppend(JSON.toJSONString(param));
            }
        }
        if(body != null && body.size() != 0) {
            body = body.stream().filter(p-> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
            if(body.size() != 0) {
                agent.setBodyAppend(JSON.toJSONString(body));
            }
        }
        int count = agentDao.update(agent);
        return count > 0;
    }

    public Map<String, Object> getAgentList(String keyWord, String status, int pageNo, int pageSize) {
        int start = (pageNo - 1) * pageSize;
        List<Agent> res = agentDao.list(keyWord, status, start, pageSize);
        List<AgentDTO> list = res.stream().map(p -> {
            AgentDTO agentDTO = new AgentDTO();
            BeanUtils.copyProperties(p, agentDTO);
            String header = p.getHeaderAppend();
            String cookie = p.getCookieAppend();
            String param = p.getUrlAppend();
            String body = p.getBodyAppend();
            agentDTO.setHeader((StringUtils.isBlank(header) || "[]".equals(header)) ? new ArrayList<>(Collections.singletonList(new Append("", ""))) : JSON.parseArray(header, Append.class));
            agentDTO.setCookie((StringUtils.isBlank(cookie) || "[]".equals(header)) ? new ArrayList<>(Collections.singletonList(new Append("", ""))) : JSON.parseArray(cookie, Append.class));
            agentDTO.setParam((StringUtils.isBlank(param) || "[]".equals(header)) ? new ArrayList<>(Collections.singletonList(new Append("", ""))) : JSON.parseArray(param, Append.class));
            agentDTO.setBody((StringUtils.isBlank(body) || "[]".equals(header)) ? new ArrayList<>(Collections.singletonList(new Append("", ""))) : JSON.parseArray(body, Append.class));
            return agentDTO;
        }).collect(Collectors.toList());
        // 获取总数
        Long count = agentDao.count(keyWord, status);
        return ImmutableMap.of("list", list, "count", count);
    }


    public Agent findAgentByUrl(String url) {
        return agentDao.findByUrl(url);
    }

}
