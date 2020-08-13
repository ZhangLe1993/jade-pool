/*
 * <<
 *  Davinci
 *  ==
 *  Copyright (C) 2016 - 2019 EDP
 *  ==
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  >>
 *
 */

package com.biubiu.agent.utils;

import com.alibaba.druid.util.StringUtils;
import com.biubiu.agent.core.exception.ServerException;
import com.biubiu.agent.core.model.SqlEntity;
import com.biubiu.agent.core.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.biubiu.agent.common.Constants.*;
import static com.biubiu.agent.core.consts.Consts.NEW_LINE_CHAR;
import static com.biubiu.agent.core.consts.Consts.SPACE;


@Slf4j
@Component
public class SqlParseUtils {

    private static final String SELECT = "select";

    private static final String WITH = "with";

    /**
     * 解析sql
     *
     * @param sqlStr           view sql 模版
     * @param sqlTempDelimiter ST 模板界定符
     * @return
     */
    public SqlEntity parseSql(String sqlStr, String sqlTempDelimiter) throws ServerException {
        if (StringUtils.isEmpty(sqlStr.trim())) {
            return null;
        }
        sqlStr = SqlUtils.filterAnnotate(sqlStr);
        sqlStr = sqlStr.replaceAll(NEW_LINE_CHAR, SPACE).trim();
        char delimiter = getSqlTempDelimiter(sqlTempDelimiter);

        Pattern p = Pattern.compile(getReg(REG_SQL_PLACEHOLDER, delimiter, false));
        Matcher matcher = p.matcher(sqlStr);

        if (!matcher.find()) {
            return new SqlEntity(sqlStr, null, null);
        }
        Map<String, Object> queryParamMap = new ConcurrentHashMap<>();
        Map<String, List<String>> authParamMap = new Hashtable<>();
        return new SqlEntity(sqlStr, queryParamMap, authParamMap);
    }

    public List<String> getSqls(String sql, boolean isQuery) {
        sql = sql.trim();
        if (StringUtils.isEmpty(sql)) {
            return null;
        }
        if (sql.startsWith(SEMICOLON)) {
            sql = sql.substring(1);
        }
        if (sql.endsWith(SEMICOLON)) {
            sql = sql.substring(0, sql.length() - 1);
        }
        List<String> list = null;
        String[] split = sql.split(SEMICOLON);
        if (split.length > 0) {
            list = new ArrayList<>();
            for (String sqlStr : split) {
                boolean select = sqlStr.toLowerCase().startsWith(SELECT) || sqlStr.toLowerCase().startsWith(WITH);
                if (isQuery) {
                    if (select) {
                        list.add(sqlStr);
                    }
                } else {
                    if (!select) {
                        list.add(sqlStr);
                    }
                }
            }
        }
        return list;
    }

    public static String rebuildSqlWithFragment(String sql) {
        if (!sql.toLowerCase().startsWith(WITH)) {
            Matcher matcher = WITH_SQL_FRAGMENT.matcher(sql);
            if (matcher.find()) {
                String withFragment = matcher.group();
                if (!StringUtils.isEmpty(withFragment)) {
                    if (withFragment.length() > 6) {
                        int lastSelectIndex = withFragment.length() - 6;
                        sql = sql.replace(withFragment, withFragment.substring(lastSelectIndex));
                        withFragment = withFragment.substring(0, lastSelectIndex);
                    }
                    sql = withFragment + SPACE + sql;
                    sql = sql.replaceAll(SPACE + "{2,}", SPACE);
                }
            }
        }
        return sql;
    }

}
