package com.example.live.service;

import com.example.live.common.BaseResult;
import com.example.live.controller.query.ContractQuery;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:48
 */
public interface ContractService {

    BaseResult<?> contractList(ContractQuery query);

    BaseResult<?> contractInfo(String id);

    BaseResult<?> contractDown(String id);

}
