package com.biubiu.agent.dao;

import com.biubiu.agent.pojo.Agent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgentDao {

    @Insert({
            "<script>",
                "insert into agents ",
                "<trim prefix='(' suffix=')' suffixOverrides=','>",
                    "<if test='id != null'>",
                        "id,",
                    "</if>",
                    "<if test='localUrl != null'>",
                        "local_url,",
                    "</if>",
                    "<if test='targetHost != null'>",
                        "target_host,",
                    "</if>",
                    "<if test='targetUrl != null'>",
                        "target_url,",
                    "</if>",
                    "<if test='headerAppend != null'>",
                        "header_append,",
                    "</if>",
                    "<if test='cookieAppend != null'>",
                        "cookie_append,",
                    "</if>",
                    "<if test='urlAppend != null'>",
                        "url_append,",
                    "</if>",
                    "<if test='bodyAppend != null'>",
                        "body_append,",
                    "</if>",
                    "<if test='status != null'>",
                        "status,",
                    "</if>",
                "</trim>",
                "values",
                "<trim prefix='(' suffix=')' suffixOverrides=','>",
                    "<if test='id != null'>",
                        "#{id},",
                    "</if>",
                    "<if test='localUrl != null'>",
                        "#{localUrl},",
                    "</if>",
                    "<if test='targetHost != null'>",
                        "#{targetHost},",
                    "</if>",
                    "<if test='targetUrl != null'>",
                        "#{targetUrl},",
                    "</if>",
                    "<if test='headerAppend != null'>",
                        "#{headerAppend},",
                    "</if>",
                    "<if test='cookieAppend != null'>",
                        "#{cookieAppend},",
                    "</if>",
                    "<if test='urlAppend != null'>",
                        "#{urlAppend},",
                    "</if>",
                    "<if test='bodyAppend != null'>",
                        "#{bodyAppend},",
                    "</if>",
                    "<if test='status != null'>",
                        "#{status},",
                    "</if>",
                "</trim>",
            "</script>"
    })
    int insert(Agent agent);

    @Update({
            "<script>",
                "update agents",
                    "<set>",
                        "<if test='localUrl != null'>",
                            "local_url = #{localUrl},",
                        "</if>",
                        "<if test='targetHost != null'>",
                            "target_host = #{targetHost},",
                        "</if>",
                        "<if test='targetUrl != null'>",
                            "target_url = #{targetUrl},",
                        "</if>",
                        "<if test='headerAppend != null'>",
                            "header_append = #{headerAppend},",
                        "</if>",
                        "<if test='cookieAppend != null'>",
                            "cookie_append = #{cookieAppend},",
                        "</if>",
                        "<if test='urlAppend != null'>",
                            "url_append = #{urlAppend},",
                        "</if>",
                        "<if test='bodyAppend != null'>",
                            "body_append = #{bodyAppend},",
                        "</if>",
                        "<if test='status != null'>",
                            "status = #{status}",
                        "</if>",
                    "</set>",
                "where id = #{id}",
            "</script>"
    })
    int update(Agent agent);

    @Select({
            "<script>",
                "select * ",
                "from agents",
                "where 1=1 ",
                "<if test='keyWord != null'>",
                    "and (local_url LIKE CONCAT('%', #{keyWord}, '%') or target_host LIKE CONCAT('%', #{keyWord}, '%') or target_url LIKE CONCAT('%', #{keyWord}, '%') )",
                "</if>",
                "<if test='status != null'>",
                    "and status = #{status}",
                "</if>",
                "order by create_time desc",
                "limit #{start}, #{end}",
            "</script>"
    })
    List<Agent> list(@Param("keyWord") String keyWord, @Param("status") String status, @Param("start") int start, @Param("end") int end);

    @Select({
        "<script>",
            "select count(id) as num ",
            "from agents",
            "where 1=1 ",
            "<if test='keyWord != null'>",
                "and (local_url LIKE CONCAT('%', #{keyWord}, '%') or target_host LIKE CONCAT('%', #{keyWord}, '%') or target_url LIKE CONCAT('%', #{keyWord}, '%') )",
            "</if>",
            "<if test='status != null'>",
                "and status = #{status}",
            "</if>",
        "</script>"
    })
    Long count(@Param("keyWord") String keyWord, @Param("status") String status);

    @Select({
            "<script>",
                "select * from agents where local_url = #{url} order by update_time desc limit 0, 1",
            "</script>"
    })
    Agent findByUrl(@Param("url") String url);

}
