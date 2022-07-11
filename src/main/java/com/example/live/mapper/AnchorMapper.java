package com.example.live.mapper;

import com.example.live.entity.Anchor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:26
 */
@Mapper
public interface AnchorMapper {

    void batchIns(List<Anchor> list);

    @Delete("truncate anchor")
    void clear();
}
