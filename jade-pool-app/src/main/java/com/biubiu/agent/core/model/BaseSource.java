package com.biubiu.agent.core.model;

import com.biubiu.agent.pojo.Source;

import java.util.List;

public abstract class BaseSource extends RecordInfo<Source> {

    public abstract String getJdbcUrl();

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract String getDatabase();

    public abstract String getDbVersion();

    public abstract List<Dict> getProperties();

    public abstract boolean isExt();
}
