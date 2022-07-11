package com.example.live.mapper;

import com.example.live.entity.LevelRight;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:29
 */
@Mapper
public interface LevelRightMapper {

    @Select("select id, level, api, path from level_right")
    List<LevelRight> levelRightList();

}
