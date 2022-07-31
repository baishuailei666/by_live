package com.example.live.mapper;

import com.example.live.vo.MerchantAuditVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:30
 */
@Mapper
public interface MerchantAuditMapper {

    List<MerchantAuditVO> merchantAuditWait(@Param("opeUserId") Integer opeUserId, @Param("status") Integer status,
                                            @Param("mobile") String mobile, @Param("shop") String shop,
                                            @Param("index") Integer index);

    int merchantAuditWaitCount(@Param("opeUserId") Integer opeUserId, @Param("status") Integer status,
                               @Param("mobile") String mobile, @Param("shop") String shop);


    void updateMerchantAudit(@Param("merchantId") String merchantId, @Param("opeUser") Integer opeUser
            ,@Param("ct")String ct,@Param("status") Integer status, @Param("reason") String reason);

    @Select("select max(ct) from `merchant_audit` where merchant_id=#{merchantId} and ope_user=#{opeUser}")
    String updateMerchantAuditCt(@Param("merchantId") String merchantId, @Param("opeUser") Integer opeUser);


    @Insert("insert into `merchant_audit`(merchant_id, ope_user, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, now(), now())")
    void merchantShopAudit(@Param("merchantId") int merchantId, @Param("opeUser") int opeUser);

}
