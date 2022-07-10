package com.example.live.mapper;

import com.example.live.entity.Merchant;
import org.apache.ibatis.annotations.*;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:29
 */
@Mapper
public interface MerchantMapper {

    @Select("select id,mobile,pwd,shop,goods,ope_user as opeUser from merchant where mobile=#{mobile}")
    Merchant getMerchant1(@Param("mobile") String mobile);

    @Insert(" insert into merchant(mobile, ope_user, login_count, lt, ct) " +
            " values(#{mobile}, #{opeUser}, 1, now(), now())")
    void creatMerchant(@Param("mobile") String mobile, @Param("opeUser") Integer opeUser);

    @Update("update merchant set lt=now() where id=#{id}")
    void updateLt(@Param("id") int id);

}
