package com.example.live.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:37
 */
@Mapper
public interface MobileCodeMapper {

    @Select("SELECT `code` FROM mobile_code WHERE mobile=#{mobile} ORDER BY ts desc limit 1")
    String getCode(@Param("mobile") String mobile);

}
