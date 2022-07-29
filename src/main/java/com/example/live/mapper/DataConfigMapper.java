package com.example.live.mapper;

import com.example.live.entity.DataConfig;
import org.apache.ibatis.annotations.*;


/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 9:24
 */
@Mapper
public interface DataConfigMapper {

    @Select("SELECT `content` FROM data_config WHERE agent_user=#{agentUser} limit 1")
    String  getConfigStr(@Param("agentUser") Integer agentUser);

    @Update("update data_config set `content`=#{content} where agent_user=#{agentUser}")
    void modifyConfig(@Param("agentUser") Integer agentUser, @Param("content") String content);

    @Select("SELECT id, `content`, agent_user as agentUser, ct FROM data_config WHERE agent_user=#{agentUser} limit 1")
    DataConfig getContent2(@Param("agentUser") Integer agentUser);


}
