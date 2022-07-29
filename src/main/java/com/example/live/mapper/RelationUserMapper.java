package com.example.live.mapper;

import com.example.live.entity.RelationUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:31
 */
@Mapper
public interface RelationUserMapper {

    @Insert("insert into relation_user(main_user_id, child_user_id) values(#{mid}, #{cid})")
    void insRelation(@Param("mid") int mid, @Param("cid") int cid);

    @Select("select child_user_id from relation_user where main_user_id=#{mid}")
    List<Integer> relationIds(@Param("mid") int mid);

    // 所有管理员从属业务员查询
    List<RelationUser> relationUserList(@Param("list") List<Integer> list);

    @Select("select main_user_id from relation_user where child_user_id=#{cid}")
    Integer getMainId(@Param("cid") int cid);

}
