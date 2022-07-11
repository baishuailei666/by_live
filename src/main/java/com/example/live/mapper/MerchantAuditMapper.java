package com.example.live.mapper;

import com.example.live.entity.MerchantAudit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:30
 */
@Mapper
public interface MerchantAuditMapper {

    @Insert("insert into(merchant_id, ope_user, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, now(), now())")
    void merchantShopAudit(@Param("merchantId") int merchantId, @Param("opeUser") int opeUser);

}
