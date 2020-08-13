package com.biubiu.agent.controller;

import com.biubiu.agent.core.model.PaginateWithQueryColumns;
import com.biubiu.agent.dto.ViewExecuteSql;
import com.biubiu.agent.service.ExecService;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "exec", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExecController {

    private final static Logger logger = LoggerFactory.getLogger(ExecController.class);

    @Autowired
    private ExecService execService;

    @PostMapping(value = "sql", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeSql(@Valid @RequestBody ViewExecuteSql executeSql) {
        try {
            List<PaginateWithQueryColumns> list = execService.executeSql(executeSql);
            return new ResponseEntity<>(ImmutableMap.of("code", "200", "data", list), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("", e);
            return new ResponseEntity<>(ImmutableMap.of("code", "500", "data", e.getMessage()), HttpStatus.OK);
        }
    }
}
