package com.example.live.mapper;

import com.example.live.entity.Video;
import org.apache.ibatis.annotations.Insert;
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

    int count(@Param("list") List<Integer> list);

    List<Video> videoList(@Param("list") List<Integer> list, @Param("from") int from, @Param("size") int size);

    @Select("select path,level,cover from video where id=#{id}")
    Video getVideo(@Param("id") int id);

    @Insert("insert into video(title, level, path, cover) values(#{title}, #{level}, #{path}, #{cover})")
    void insVideo(@Param("title") String title, @Param("level") int level, @Param("path") String path, @Param("cover") String cover);
}
