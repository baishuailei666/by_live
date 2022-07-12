package com.example.live.mapper;

import com.example.live.vo.MerchantAuditVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:30
 */
@Mapper
public interface MerchantAuditMapper {

    List<MerchantAuditVO> merchantAuditWait(@Param("opeUserId") Integer opeUserId, @Param("status") String status,
                                            @Param("mobile") String mobile, @Param("shop") String shop,
                                            @Param("index") Integer index, @Param("size") Integer size);

    int merchantAuditWaitCount(@Param("opeUserId") Integer opeUserId, @Param("status") String status,
                               @Param("mobile") String mobile, @Param("shop") String shop);


    void updateMerchantAudit(@Param("shopId") String shopId, @Param("status") String status, @Param("reason") String reason);

    @Insert("insert into(merchant_id, ope_user, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, now(), now())")
    void merchantShopAudit(@Param("merchantId") int merchantId, @Param("opeUser") int opeUser);

}
