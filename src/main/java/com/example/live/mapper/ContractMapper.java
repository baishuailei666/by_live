package com.example.live.mapper;

import com.example.live.controller.query.ContractQuery;
import com.example.live.entity.Contract;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:27
 */
@Mapper
public interface ContractMapper {

    @Insert("insert into contract(document_id, ct, ut, sign_type, buy_type, ope_user, merchant_id) " +
            "values(#{documentId}, now(), now(), #{signType}, #{buyType}, #{opeUser}, #{merchantId})")
    void insContract(Contract contract);

    int contractCount(ContractQuery query);

    List<Contract> contractList(ContractQuery query);

}
