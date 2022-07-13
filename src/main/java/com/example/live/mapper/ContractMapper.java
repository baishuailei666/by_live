package com.example.live.mapper;

import com.example.live.entity.Contract;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:27
 */
@Mapper
public interface ContractMapper {

    @Insert("insert into contract(company, tax, owner, mobile, sign_type, remark, buy_type, ope_user, merchant_id) " +
            "values(#{company}, #{tax}, #{owner}, #{mobile}, #{signType}, #{remark}, #{buyType}, #{opeUser}, #{merchantId})")
    void insContract(Contract contract);

    void modifyContract(Contract contract);

}
