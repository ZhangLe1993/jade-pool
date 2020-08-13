package com.biubiu.agent.service;

import com.alibaba.fastjson.JSONObject;
import com.biubiu.agent.core.exception.ServerException;
import com.biubiu.agent.core.exception.SourceException;
import com.biubiu.agent.core.model.QueryColumn;
import com.biubiu.agent.core.model.TableInfo;
import com.biubiu.agent.core.utils.SqlUtils;
import com.biubiu.agent.dao.SourceDao;
import com.biubiu.agent.dto.SourceConfig;
import com.biubiu.agent.dto.SourceCreate;
import com.biubiu.agent.enums.SourceTypeEnum;
import com.biubiu.agent.model.DBTables;
import com.biubiu.agent.pojo.Source;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MetaService {

    @Autowired
    private SqlUtils sqlUtils;

    @Resource
    private SourceDao sourceDao;

    /**
     * 创建source
     *
     * @param sourceCreate
     * @return
     */
    @Transactional
    public Source createSource(SourceCreate sourceCreate) throws NotFoundException, ServerException {
        String name = sourceCreate.getName();
        checkIsExist(name, null);
        if (null == SourceTypeEnum.typeOf(sourceCreate.getType())) {
            throw new ServerException("Invalid source type");
        }
        SourceConfig config = sourceCreate.getConfig();
        // 测试连接
        if (!testConnection(config)) {
            throw new ServerException("test source connection fail");
        }
        Source source = new Source().createdUk("105281");
        BeanUtils.copyProperties(sourceCreate, source);
        source.setConfig(JSONObject.toJSONString(config));
        if (sourceDao.insert(source) != 1) {
            log.info("create source fail:{}", source.toString());
            throw new ServerException("create source fail");
        }
        return source;
    }

    private boolean testConnection(SourceConfig config) {
        return sqlUtils.init(
                config.getUrl(),
                config.getUsername(),
                config.getPassword(),
                config.getVersion(),
                config.getProperties(),
                config.isExt()
        ).testConnection();
    }

    private void checkIsExist(String name, Long id) {
        if (isExist(name, id)) {
            alertNameTaken(name);
        }
    }

    public boolean isExist(String name, Long id) {
        Long sourceId = sourceDao.getByName(name);
        if (null != id && null != sourceId) {
            return !id.equals(sourceId);
        }
        return null != sourceId && sourceId > 0L;
    }

    protected void alertNameTaken(String name) throws ServerException {
        log.warn("the source name ({}) is already taken", name);
        throw new ServerException("the " + name + " name is already taken");
    }

    public List<String> getSchemas(Long id) throws NotFoundException {
        List<DBTables> list = new ArrayList<>();
        Source source = getSource(id);
        List<String> expect = Arrays.asList("information_schema", "mysql", "sys", "performance_schema");
        return sqlUtils.init(source).getDatabases().stream().filter(p -> !expect.contains(p)).collect(Collectors.toList());
    }


    public List<DBTables> getTree(Long id) throws NotFoundException {
        List<DBTables> list = new ArrayList<>();
        Source source = getSource(id);
        List<String> schemas = sqlUtils.init(source).getDatabases();
        for(String schema : schemas) {
            DBTables dbTable = new DBTables(schema);
            List<QueryColumn> tableList = null;
            try {
                tableList = sqlUtils.init(source).getTableList(schema);
            } catch (SourceException e) {
                throw new ServerException(e.getMessage());
            }
            if (null != tableList) {
                dbTable.setTables(tableList);
            }
            list.add(dbTable);
        }
        return list;
    }

    /**
     * 获取Source的data tables
     *
     * @param id
     * @param dbName
     * @return
     */
    public DBTables getSourceTables(Long id, String dbName) throws NotFoundException {
        DBTables dbTable = new DBTables(dbName);
        Source source = getSource(id);
        List<QueryColumn> tableList = null;
        try {
            tableList = sqlUtils.init(source).getTableList(dbName);
        } catch (SourceException e) {
            throw new ServerException(e.getMessage());
        }
        if (null != tableList) {
            dbTable.setTables(tableList);
        }
        return dbTable;
    }

    private Source getSource(Long id) throws NotFoundException {
        Source source = sourceDao.getById(id);
        if (null == source) {
            log.warn("source (:{}) is not found", id);
            throw new NotFoundException("this source is not found");
        }
        return source;
    }

    public TableInfo getTableInfo(Long id, String dbName, String tableName) throws NotFoundException {
        Source source = getSource(id);
        TableInfo tableInfo = null;
        try {
            tableInfo = sqlUtils.init(source).getTableInfo(dbName, tableName);
        } catch (SourceException e) {
            e.printStackTrace();
            throw new ServerException(e.getMessage());
        }
        return tableInfo;
    }

}
