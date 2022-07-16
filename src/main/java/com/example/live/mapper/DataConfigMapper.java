package com.example.live.mapper;

import com.example.live.entity.DataConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 9:24
 */
@Mapper
public interface DataConfigMapper {

    @Select("SELECT `content` FROM data_config WHERE agent_user=#{agentUser} limit 1")
    String  getConfigStr(@Param("agentUser") Integer agentUser);

    List<DataConfig> configList(@Param("agentUser") Integer agentUser);

    @Insert("insert into data_config(agent_user, `content`) values(#{agentUser}, #{content})")
    void insConfig(@Param("agentUser") Integer agentUser, @Param("content") String content);

    @Update("update data_config set `content`=#{content} where id=#{id} and agent_user=#{agentUser})")
    void modifyConfig(@Param("id") Integer id, @Param("agentUser") Integer agentUser, @Param("content") String content);

    @Select("SELECT id, `content`, agent_user as agentUser FROM data_config WHERE id=#{id}")
    DataConfig getContent(@Param("id") Integer id);

    @Select("select id,agent_user,content from data_config")
    List<DataConfig> getDataConfigs();

}
