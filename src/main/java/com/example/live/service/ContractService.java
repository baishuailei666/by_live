package com.example.live.service;

import com.example.live.common.BaseResult;
import com.example.live.controller.query.ContractQuery;

import javax.servlet.http.HttpServletResponse;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:48
 */
public interface ContractService {

    BaseResult<?> contractList(ContractQuery query);

    BaseResult<?> contractView(String id, HttpServletResponse response);

    BaseResult<?> contractDown(String id);

}
