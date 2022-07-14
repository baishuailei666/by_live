package com.example.live.mapper;

import com.example.live.controller.query.ContractQuery;
import com.example.live.entity.Contract;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    int contractCount(ContractQuery query);

    List<Contract> contractList(ContractQuery query);

    @Select("select id, merchant_id as merchantId, ope_user as opeUser, buy_type as buyType" +
            " ct, ut, remark, sign_status as signStatus, sign_type as signType, company, tax, owner, mobile" +
            " from contract where id=#{id}")
    Contract getContract(@Param("id") Integer id);


}
