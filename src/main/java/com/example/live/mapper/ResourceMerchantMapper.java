package com.example.live.mapper;

import com.example.live.entity.Anchor;
import com.example.live.entity.ResourceMerchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:32
 */
@Mapper
public interface ResourceMerchantMapper {

    void batchIns(List<ResourceMerchant> list);

}
