package com.biubiu.agent.service;

import com.biubiu.agent.core.exception.NotFoundException;
import com.biubiu.agent.dao.SourceDao;
import com.biubiu.agent.pojo.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SourceService {

    @Resource
    private SourceDao sourceDao;

    public Source getSource(Long id) {
        Source source = sourceDao.getById(id);
        if (null == source) {
            log.error("source (:{}) not found", id);
            throw new NotFoundException("source is not found");
        }
        return source;
    }
}
