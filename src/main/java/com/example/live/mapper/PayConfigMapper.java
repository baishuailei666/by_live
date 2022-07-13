package com.example.live.mapper;

import com.example.live.entity.PayConfig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/9 15:28
 */
@Mapper
public interface PayConfigMapper {

    PayConfig getConfig(@Param("userId") int userId);

    List<PayConfig> configList(@Param("agentUser") Integer agentUser);

    void insConfig(PayConfig payConfig);

    @Delete("delete from pay_config where id=#{id}")
    void delConfig(@Param("id") Integer id);



}
