package com.example.live.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 9:24
 */
@Mapper
public interface ConfigurationMapper {

    @Select("SELECT `content` FROM configuration WHERE agent_user=#{agentUser}")
    String  getConfigStr(@Param("agentUser") Integer agentUser);

}
