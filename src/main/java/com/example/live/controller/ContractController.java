package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.controller.query.ContractQuery;
import com.example.live.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 合同
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/14 10:41
 */
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 合同列表
     * @param query page、mobile、shop、company
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> contractList(@RequestBody ContractQuery query) {
        return contractService.contractList(query);
    }

    /**
     * 合同详情
     * @param id
     * @return
     */
    @GetMapping("/info")
    public BaseResult<?> contractInfo(@RequestParam("id") Integer id) {
        return contractService.contractInfo(id);
    }

    /**
     * 合同下载
     * @param id
     * @return
     */
    @GetMapping("/down")
    public BaseResult<?> contractDown(@RequestParam("id") Integer id) {
        return contractService.contractDown(id);
    }

}
