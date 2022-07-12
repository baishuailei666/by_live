package com.example.live.mapper;

import com.example.live.entity.Anchor;
import org.apache.ibatis.annotations.*;

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

    @Select("select id, nickname, img, author_id as authorId, gender, level, fans, url" +
            ", category, goods, sales, introduce, live, live_day as liveDay, view, avg_dur as avgDur" +
            ", rate, price, score from anchor where id=#{id}")
    Anchor anchorInfo(@Param("id") Integer id);


    List<Anchor> anchorList();

    // 已查看主播
    List<Anchor> anchorCollect(@Param("merchantId") Integer merchantId);

    @Update("update merchant_anchor set add=1 where merchant_id=#{merchantId} and anchor_id=#{id}")
    void anchorAddWx(@Param("merchantId") Integer merchantId, @Param("id") Integer id);

    @Delete("delete from merchant_anchor where merchant_id=#{merchantId} and anchor_id=#{id}")
    void anchorRemove(@Param("merchantId") Integer merchantId, @Param("id") Integer id);


}
