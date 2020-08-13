package com.biubiu.agent.service;

import com.alibaba.druid.util.StringUtils;
import com.biubiu.agent.core.exception.NotFoundException;
import com.biubiu.agent.core.exception.ServerException;
import com.biubiu.agent.core.model.PaginateWithQueryColumns;
import com.biubiu.agent.core.model.SqlEntity;
import com.biubiu.agent.core.utils.CollectionUtils;
import com.biubiu.agent.core.utils.SqlUtils;
import com.biubiu.agent.dto.ViewExecuteSql;
import com.biubiu.agent.pojo.Source;
import com.biubiu.agent.utils.SqlParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.biubiu.agent.core.consts.Consts.COMMA;

@Service
public class ExecService {

    @Autowired
    private SqlParseUtils sqlParseUtils;

    @Autowired
    private SqlUtils sqlUtils;

    @Value("${sql_template_delimiter:$}")
    private String sqlTempDelimiter;

    @Autowired
    private SourceService sourceService;

    public List<PaginateWithQueryColumns> executeSql(ViewExecuteSql executeSql) throws NotFoundException, ServerException {
        List<PaginateWithQueryColumns> list = new ArrayList<>();
        Source source = sourceService.getSource(executeSql.getSourceId());
        //结构化Sql
        try {
            SqlEntity sqlEntity = sqlParseUtils.parseSql(executeSql.getSql(),  sqlTempDelimiter);
            if (null == sqlUtils || null == sqlEntity || StringUtils.isEmpty(sqlEntity.getSql())) {
                return list;
            }
            if (!CollectionUtils.isEmpty(sqlEntity.getQuaryParams())) {
                sqlEntity.getQuaryParams().forEach((k, v) -> {
                    if (v instanceof List && ((List) v).size() > 0) {
                        v = ((List) v).stream().collect(Collectors.joining(COMMA)).toString();
                    }
                    sqlEntity.getQuaryParams().put(k, v);
                });
            }
            SqlUtils sqlUtils = this.sqlUtils.init(source);
            List<String> executeSqlList = sqlParseUtils.getSqls(sqlEntity.getSql(), false);
            List<String> querySqlList = sqlParseUtils.getSqls(sqlEntity.getSql(), true);
            if (!CollectionUtils.isEmpty(executeSqlList)) {
                executeSqlList.forEach(sql -> sqlUtils.execute(sql));
            }
            if (!CollectionUtils.isEmpty(querySqlList)) {
                for (String sql : querySqlList) {
                    sql = SqlParseUtils.rebuildSqlWithFragment(sql);
                    PaginateWithQueryColumns paginateWithQueryColumns = sqlUtils.syncQuery4Paginate(sql, null, null, null, executeSql.getLimit(),null);
                    list.add(paginateWithQueryColumns);
                }
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
        return list;
    }

}
