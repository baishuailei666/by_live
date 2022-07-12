package com.example.live.mapper;

import com.example.live.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:32
 */
@Mapper
public interface VideoMapper {

    List<Video> videoList(@Param("type") Integer type);

    @Select("select path from video where id=#{id}")
    String video1(@Param("id") Integer id);
}
