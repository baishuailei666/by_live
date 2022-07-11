package com.example.live.mapper;

import com.example.live.entity.MobileCode;
import org.apache.ibatis.annotations.Delete;
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

    @Select("SELECT `code`, ts FROM mobile_code WHERE mobile=#{mobile} ORDER BY ts desc limit 1")
    MobileCode getCodeEnt(@Param("mobile") String mobile);

    @Delete("truncate mobile_code")
    void clear();

}
