package com.biubiu.agent.controller;

import com.biubiu.agent.annotation.SystemLog;
import com.biubiu.agent.core.model.TableInfo;
import com.biubiu.agent.dto.SourceCreate;
import com.biubiu.agent.model.DBTables;
import com.biubiu.agent.pojo.Source;
import com.biubiu.agent.service.MetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "source")
public class SourceController {

    private final static Logger logger = LoggerFactory.getLogger(SourceController.class);

    @Autowired
    private MetaService metaService;

    @SystemLog(description = "创建数据源")
    @PostMapping(value = "")
    public ResponseEntity<?> addSource(@RequestBody SourceCreate sourceCreate) {
        try {
            Source source = metaService.createSource(sourceCreate);
            return new ResponseEntity<>("创建成功", HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>("系统异常",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @SystemLog(description = "获取数据源树")
    @GetMapping(value = "tree")
    public ResponseEntity<?> getSourceTree(@RequestParam(name = "id") Long id) {
        try {
            List<DBTables> tree = metaService.getTree(id);
            return new ResponseEntity<>(tree, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SystemLog(description = "获取链接的所有数据库")
    @GetMapping(value = "schema")
    public ResponseEntity<?> getSourceSchema(@RequestParam(name = "id") Long id) {
        try {
            List<String> schemas = metaService.getSchemas(id);
            return new ResponseEntity<>(schemas, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SystemLog(description = "获取制定数据库的所有表")
    @GetMapping(value = "/schema/tables")
    public ResponseEntity<?> getSourceTable(@RequestParam(name = "id") Long id, @RequestParam(name = "schema") String schema) {
        try {
            DBTables dbTables = metaService.getSourceTables(id, schema);
            return new ResponseEntity<>(dbTables, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SystemLog(description = "获取制定数据库制定表下的所有字段")
    @GetMapping(value = "/schema/table/fields")
    public ResponseEntity<?> getSourceField(@RequestParam(name = "id") Long id, @RequestParam(name = "schema") String schema, @RequestParam(name = "table") String table) {
        try {
            TableInfo tableInfo = metaService.getTableInfo(id, schema, table);
            return new ResponseEntity<>(tableInfo, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("", e);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
