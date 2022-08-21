package com.example.live.mapper;

import com.example.live.controller.query.ContractQuery;
import com.example.live.entity.Contract;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:27
 */
@Mapper
public interface ContractMapper {

    @Insert("insert into contract(flow_id, document_id, document_name, ct, ut, sign_type, buy_type, ope_user, merchant_id) " +
            "values(#{flowId}, #{documentId}, #{documentName}, now(), now(), #{signType}, #{buyType}, #{opeUser}, #{merchantId})")
    void insContract(Contract contract);

    int contractCount(ContractQuery query);

    List<Contract> contractList(ContractQuery query);

    void updateStatus1(List<Integer> ids);

    @Update("update contract set sign_status=1 where flow_id=#{flowId}")
    void updateStatus2(@Param("flowId") String flowId);

    // 30天内合同份数
    int contractMonth(@Param("mid") Integer mid);

}
