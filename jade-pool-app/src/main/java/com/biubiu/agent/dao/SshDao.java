package com.biubiu.agent.dao;

import com.biubiu.agent.pojo.Ssh;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SshDao {

    @Insert({
            "<script>",
                "insert into ssh",
                    "<trim prefix='(' suffix=')' suffixOverrides=','>",
                        "`name`,",
                        "<if test='description != null'>",
                            "description,",
                        "</if>",
                        "<if test='config != null'>",
                            "`config`,",
                        "</if>",
                        "`parent_id`,",
                        "`type`,",
                        "`create_uk`,",
                        "`update_uk`,",
                    "</trim>",
                    "<trim prefix='values (' suffix=')' suffixOverrides=','>",
                        "#{name},",
                        "<if test='description != null'>",
                            "#{description},",
                        "</if>",
                        "<if test='config != null'>",
                            "#{config},",
                        "</if>",
                        "#{parentId},",
                        "#{type},",
                        "#{createUk},",
                        "#{createUk},",
                    "</trim>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert();

    @Select({
            "select * from ssh"
    })
    List<Ssh> list();

    @Delete({
            "delete from ssh where id = #{id}"
    })
    int delete(@Param("id") Long id);

    @Delete({
            "delete from ssh where id  = #{id}"
    })
    int batchDelete(@Param("id") Long id);

}
