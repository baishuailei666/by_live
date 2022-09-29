package com.example.live.mapper;

import com.example.live.entity.MerchantSign;
import org.apache.ibatis.annotations.*;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/26 16:01
 */
@Mapper
public interface MerchantSignMapper {

    @Select("select ms.id, ms.subject, ms.person, ms.mobile, ms.tax, m.shop_id as shopId, m.shop from merchant_sign ms" +
            " left join merchant m on m.id=ms.merchant_id" +
            " where ms.merchant_id=#{mid} limit 1")
    MerchantSign getSignMerchant(@Param("mid") int mid);

    @Select("select id, subject, person, mobile, tax from merchant_sign where merchant_id=#{mid} limit 1")
    MerchantSign getOne(@Param("mid") int mid);

    @Insert(" insert into merchant_sign(subject, person, mobile, tax, merchant_id) " +
            " values(#{subject}, #{person}, #{mobile}, #{tax}, #{merchantId})")
    void insOne(MerchantSign sign);

    @Update("update merchant_sign set subject=#{subject}, mobile=#{mobile}, tax=#{tax}, person=#{person} where id=#{id} and merchant_id=#{merchantId}")
    void modifyOne(MerchantSign sign);

}
