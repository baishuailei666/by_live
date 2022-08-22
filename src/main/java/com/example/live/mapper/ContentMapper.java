package com.example.live.mapper;

import com.example.live.entity.Content;
import com.example.live.vo.ContentVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:27
 */
@Mapper
public interface ContentMapper {

    // 查询5s内的消息
    @Select("select count(1) from `content` where type=3 and source=#{source} and oid=#{id} and ts between date_add(now(), interval - 5 second) and now() ")
    int getMsg3(@Param("id") Integer id, @Param("source") int source);

    @Insert("insert into `content`(oid, rid, note, type, source) values(#{oid}, #{rid}, #{note}, #{type}, #{source})")
    void insContent(@Param("oid") int oid, @Param("rid") int rid, @Param("note") String note, @Param("type") int type, @Param("source") int source);

    @Select("select id, note, ts from `content` where type=3 and source=1 and oid=#{oid} order by ts desc limit #{page}, 10 ")
    List<Content> contentList(@Param("oid") int oid, @Param("page") int page);

    @Select("select note, ts from `content` where oid=#{oid} and rid=#{rid} and type=#{type}")
    List<ContentVO> contentListParam(@Param("oid") int oid, @Param("rid") int rid, @Param("type") int type);

    // 查询备注、跟进记录等
    List<Content> contentList2(@Param("oid") Integer oid, @Param("rids") List<Integer> rids, @Param("type") int type);

}
