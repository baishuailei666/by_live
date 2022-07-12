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

    @Select("select id,mobile,pwd,shop,goods,ope_user as opeUser, login_count as loginCount from merchant where mobile=#{mobile}")
    Merchant getMerchant1(@Param("mobile") String mobile);

    @Select("select m.id,m.mobile,m.shop,m.introduce,ma.status,ma.remark from merchant m" +
            " left join merchant_audit ma on ma.merchant_id=m.id" +
            " where m.id=#{id}")
    Merchant getMerchant2(@Param("id") int id);

    @Select("select count(1) from merchant where shop_id=#{shopId}")
    int existShop(@Param("shopId") String shopId);

    @Select("select id from merchant where shop_id=#{shopId}")
    int shopMerchant(@Param("shopId") String shopId);

    @Update("update merchant set shop_id=#{shopId}, shop=#{shop}, goods=#{goods}, introduce=#{introduce} where id=#{merchantId}")
    void bindShop(@Param("merchantId") int merchantId, @Param("shopId") String shopId
            , @Param("shop") String shop, @Param("goods") String goods, @Param("introduce") String introduce);

    @Update("update merchant set shop_id=#{shopId}, shop=#{shop}, goods=#{goods}, introduce=#{introduce} where id=#{merchantId}")
    void modifyShop(@Param("merchantId") int merchantId, @Param("shopId") String shopId
            , @Param("shop") String shop, @Param("goods") String goods, @Param("introduce") String introduce);

    @Update("update merchant set shop_id=null, shop=null, goods=null, introduce=null where id=#{merchantId} and shop_id=#{shopId}")
    void delShop(@Param("merchantId") int merchantId, @Param("shopId") String shopId);

    @Insert(" insert into merchant(mobile, pwd, ope_user, login_count, lt, ct) " +
            " values(#{mobile}, #{opeUser}, 1, now(), now())")
    void creatMerchant(@Param("mobile") String mobile, @Param("pwd") String pwd, @Param("opeUser") Integer opeUser);

    @Update("update merchant set lt=now(), login_count=#{lc} where id=#{id}")
    void updateLt(@Param("id") int id, @Param("lc") int lc);

    @Update("update merchant set pwd=#{pwd} where mobile=#{mobile}")
    void modifyPwd(@Param("mobile") String mobile, @Param("pwd") String pwd);

    @Update("update merchant set shop_status = '已认证' where shop_id = #{shopId}")
    void updateMerchantCheck(@Param("shopId")String shopId);

    /**
     * 查询商户列表，包含手机、店铺名、状态条件筛选
     * @param opeUserId
     * @param mobile
     * @param shop
     * @param shopStatus
     * @return
     */
    @Select({"<script>" +
            " SELECT id,mobile,shop,shop_id,shop_status,ope_user,days,login_count,lt,ct FROM `merchant` "
            + " <where>"
            + " ope_user = #{opeUserId}"
            + " <if test=' mobile != null and mobile != \"\"' > AND mobile = #{mobile}</if>"
            + " <if test=' shop != null and shop != \"\"'> AND shop LIKE CONCAT('%',#{shop},'%')</if>"
            + " <if test=' shopStatus != null and shopStatus != \"\" '> AND shop_status = #{shopStatus}</if>"
            + " </where>"
            + "ORDER BY ct ASC "
            + "LIMIT #{index},#{size}"
            + " </script> "})
    List<Merchant> getMerchantListByParams(@Param("opeUserId") Integer opeUserId, @Param("mobile")String mobile, @Param("shop") String shop, @Param("shopStatus") String shopStatus,@Param("index") Integer index,@Param("size")Integer size);

    @Select({"<script>" +
            " SELECT COUNT(*) FROM `merchant` "
            + " <where>"
            + " ope_user = #{opeUserId}"
            + " <if test=' mobile != null and mobile != \"\"' > AND mobile = #{mobile}</if>"
            + " <if test=' shop != null and shop != \"\"'> AND shop LIKE CONCAT('%',#{shop},'%')</if>"
            + " <if test=' shopStatus != null and shopStatus != \"\" '> AND shop_status = #{shopStatus}</if>"
            + " </where>"
            + " </script> "})
    int getMerchantListByParamsCount(@Param("opeUserId") Integer opeUserId, @Param("mobile")String mobile, @Param("shop") String shop, @Param("shopStatus") String shopStatus);


}
