package com.example.live.mapper;


import com.example.live.entity.ResourceMerchant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:32
 */
@Mapper
public interface ResourceMerchantMapper {

    void batchIns(List<ResourceMerchant> list);

    @Delete("truncate resource_merchant")
    void clear();

    List<ResourceMerchant> resourceList(@Param("agentUser") Integer agentUser, @Param("opeUser") Integer opeUser
            , @Param("intention") Integer intention, @Param("page") int page);

    int resourceCount(@Param("agentUser") Integer agentUser, @Param("opeUser") Integer opeUser
            , @Param("intention") Integer intention);

    // 未联系-0、跟进中-1、已拒绝-3
    List<ResourceMerchant> taskResource();

    // 资源进行分配
    void taskDistribution(List<ResourceMerchant> list);

    // 需要清理的数据
    @Delete(" delete from resource_merchant where intention = 2")
    void needClearList();

}
