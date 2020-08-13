package com.biubiu.agent.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class ConnectionUtils {

    public static final DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    public static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static String buildUrl(String host) {
        return "jdbc:mysql";
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet)
    {
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                // ignore
            }
        }
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                // ignore
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                // ignore
            }
        }
    }

    public static String quoted(String catalog, String schema, String table, String identifierQuote)
    {
        StringBuilder sb = new StringBuilder();
        if (!isNullOrEmpty(catalog)) {
            sb.append(quoted(catalog, identifierQuote)).append(".");
        }
        if (!isNullOrEmpty(schema)) {
            sb.append(quoted(schema, identifierQuote)).append(".");
        }
        sb.append(quoted(table, identifierQuote));
        return sb.toString();
    }

    public static String quoted(String schema, String table, String identifierQuote)
    {
        StringBuilder sb = new StringBuilder();
        if (!isNullOrEmpty(schema)) {
            sb.append(quoted(schema, identifierQuote)).append(".");
        }
        sb.append(quoted(table, identifierQuote));
        return sb.toString();
    }

    public static String quoted(String name, String identifierQuote)
    {
        name = name.replace(identifierQuote, identifierQuote + identifierQuote);
        return identifierQuote + name + identifierQuote;
    }

    public static String escapeNamePattern(String name, String escape)
    {
        if ((name == null) || (escape == null)) {
            return name;
        }
        checkArgument(!escape.equals("_"), "Escape string must not be '_'");
        checkArgument(!escape.equals("%"), "Escape string must not be '%'");
        name = name.replace(escape, escape + escape);
        name = name.replace("_", escape + "_");
        name = name.replace("%", escape + "%");
        return name;
    }

    public static PreparedStatement getPgsqlPreparedStatement(Connection connection, String sql)
            throws SQLException
    {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setFetchSize(1000);
        return statement;
    }

    public static String getCurrentTime()
    {
        return format1.format(new Date());
    }

    public static int index(List<String> list, String value) {
        int i;
        int length = list.size();
        for(i = 0; i < length; i++) {
            if(value.equalsIgnoreCase(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String toString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
