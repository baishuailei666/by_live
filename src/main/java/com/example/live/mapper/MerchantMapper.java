package com.example.live.mapper;

import com.example.live.entity.Merchant;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:29
 */
@Mapper
public interface MerchantMapper {

    List<Merchant> merchantSearch();

    List<Merchant> merchantList(@Param("ids") List<Integer> ids);

    @Select("select m.id,m.mobile,m.pwd,m.shop_id as shopId, m.days, m.shop,m.ope_user as opeUser, " +
            "m.login_count as loginCount, m.shop_status as shopStatus, m.ct as ct, m.lt as lt from merchant m" +
            " where m.mobile=#{mobile}")
    Merchant getMerchant1(@Param("mobile") String mobile);

    @Select("select m.id,m.mobile,m.shop_id as shopId, m.shop,m.goods as goods,m.introduce,ma.status as auditStatus,ma.remark as auditRemark from merchant m" +
            " left join merchant_audit ma on ma.merchant_id=m.id" +
            " where m.id=#{id} order by ma.ct desc limit 1")
    Merchant getMerchant2(@Param("id") int id);

    @Select("select m.id,m.mobile,m.shop_id as shopId,m.shop,m.introduce,m.shop_status as shopStatus,m.days from merchant m" +
            " where m.id=#{id}")
    Merchant getMerchant3(@Param("id") int id);

    @Select("select id from merchant where shop_id=#{shopId}")
    Integer existShop(@Param("shopId") String shopId);

    @Select("select id from merchant where shop_id=#{shopId}")
    int shopMerchant(@Param("shopId") String shopId);

    @Update("update merchant set shop_id=#{shopId}, shop=#{shop}, goods=#{goods}, introduce=#{introduce} where id=#{merchantId}")
    void bindShop(@Param("merchantId") int merchantId, @Param("shopId") String shopId
            , @Param("shop") String shop, @Param("goods") String goods, @Param("introduce") String introduce);

    @Update("update merchant set shop_id=#{shopId}, shop=#{shop}, goods=#{goods}, introduce=#{introduce} where id=#{merchantId}")
    void modifyShop(@Param("merchantId") int merchantId, @Param("shopId") String shopId
            , @Param("shop") String shop, @Param("goods") String goods, @Param("introduce") String introduce);

    @Update("update merchant set shop_id=#{shopId}, shop=#{shop}, goods=#{goods}, introduce=#{introduce}, shop_status=#{shopStatus} where id=#{merchantId}")
    void modifyShop2(@Param("merchantId") int merchantId, @Param("shopId") String shopId
            , @Param("shop") String shop, @Param("goods") String goods, @Param("introduce") String introduce, @Param("shopStatus") String shopStatus);


    @Insert(" insert into merchant(mobile, pwd, ope_user, login_count, lt, ct) " +
            " values(#{mobile},#{pwd},#{opeUser}, 1, now(), now())")
    void creatMerchant(@Param("mobile") String mobile, @Param("pwd") String pwd, @Param("opeUser") Integer opeUser);

    @Select("select LAST_INSERT_ID()")
    int lastId();

    @Update("update merchant set lt=now(), login_count=#{lc} , days=#{days} where id=#{id}")
    void updateLt(@Param("id") int id, @Param("lc") int lc, @Param("days") int days);

    @Update("update merchant set pwd=#{pwd} where mobile=#{mobile}")
    void modifyPwd(@Param("mobile") String mobile, @Param("pwd") String pwd);

    @Update("update merchant set shop_status = '已认证' where id=#{merchantId}")
    void updateMerchantCheck(@Param("merchantId") String merchantId);

    // 支付成功修改操作
    @Update("update merchant set shop_status=#{shopStatus}, days=#{days} where id=#{merchantId}")
    void updateMerchantDays(@Param("merchantId") Integer merchantId, @Param("shopStatus") String shopStatus, @Param("days") Integer days);

    List<Merchant> getMerchantListByParams(@Param("ids") List<Integer> ids, @Param("mobile") String mobile, @Param("shop") String shop, @Param("shopStatus") String shopStatus, @Param("index") Integer index);

    int getMerchantListByParamsCount(@Param("ids") List<Integer> ids, @Param("mobile") String mobile, @Param("shop") String shop, @Param("shopStatus") String shopStatus);


}
