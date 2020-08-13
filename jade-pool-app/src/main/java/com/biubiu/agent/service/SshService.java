package com.biubiu.agent.service;

import com.alibaba.fastjson.JSONObject;
import com.biubiu.agent.dao.SshDao;
import com.biubiu.agent.model.Remote;
import com.biubiu.agent.model.SshNode;
import com.biubiu.agent.pojo.Ssh;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SshService {

    @Resource
    private SshDao sshDao;

    public List<SshNode> getSshTree() {
        List<Ssh> list = sshDao.list();
        return recursion(list, -1L);
    }

    private List<SshNode> recursion(List<Ssh> list, Long targetId) {
        List<SshNode> target = new ArrayList<>();
        List<Ssh> filters = list.stream().filter(v -> v.getParentId().equals(targetId)).collect(Collectors.toList());
        for (Ssh ssh : filters) {
            SshNode sshNode = new SshNode();
            Long id = ssh.getId();
            sshNode.setId(id);
            sshNode.setName(ssh.getName());
            String type = ssh.getType();
            sshNode.setType(type);
            if ("FOLDER".equalsIgnoreCase(type)) {
                sshNode.setIcon("el-icon-folder-opened");
                // 追加孩子节点
                List<SshNode> children = recursion(list, id);
                sshNode.setChildren(children);
            } else {
                sshNode.setIcon("el-icon-tickets");
                Remote remote = parse(ssh);
                sshNode.setHost(remote.getHost());
            }
            target.add(sshNode);
        }
        return target;
    }

    private Remote parse(Ssh ssh) {
        Remote remote = null;
        String config = ssh.getConfig();
        try{
            remote = JSONObject.parseObject(config, Remote.class);
        }catch(Exception e) {
            log.error("", e);
        }
        return remote;
    }


}
