package com.biubiu.agent.dao;

import com.biubiu.agent.pojo.Source;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SourceDao {

    @Insert({
            "<script>",
                "insert into source",
                "<trim prefix='(' suffix=')' suffixOverrides=','>",
                    "`name`,",
                    "<if test='description != null'>",
                        "description,",
                    "</if>",
                    "`type`,",
                    "`config`,",
                    "`create_uk`,",
                    "`update_uk`,",
                "</trim>",
                "<trim prefix='values (' suffix=')' suffixOverrides=','>",
                    "#{name},",
                    "<if test='description != null'>",
                        "#{description},",
                    "</if>",
                    "#{type},",
                    "#{config},",
                    "#{createUk},",
                    "#{createUk},",
                "</trim>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Source source);

    @Delete({"delete from `source` where id = #{id}"})
    int deleteById(@Param("id") Long id);

    @Select({"select * from `source` where id = #{id}"})
    Source getById(@Param("id") Long id);

    @Update({
            "update `source`",
            "set `name` = #{name,jdbcType=VARCHAR},",
            "`description` = #{description,jdbcType=VARCHAR},",
            "`type` = #{type,jdbcType=VARCHAR},",
            "`project_id` = #{projectId,jdbcType=BIGINT},",
            "`config` = #{config,jdbcType=LONGVARCHAR},",
            "`update_by` = #{updateBy,jdbcType=BIGINT},",
            "`update_time` = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int update(Source source);

    @Select({"select id from `source` where name = #{name}"})
    Long getByName(@Param("name") String name);

    @Select({"select * from `source` where project_id = #{projectId}"})
    List<Source> getByProject(@Param("projectId") Long projectId);


    int insertBatch(@Param("list") List<Source> sourceList);

    @Delete({"delete from `source` where project_id = #{projectId}"})
    int deleteByProject(@Param("projectId") Long projectId);
}
