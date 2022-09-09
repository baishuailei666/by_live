package com.example.live.controller;

import com.example.live.common.BaseResult;
import com.example.live.controller.query.ContractQuery;
import com.example.live.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
     *
     * @param query page、mobile、shop、company
     * @return
     */
    @RequestMapping("/list")
    public BaseResult<?> contractList(@RequestBody ContractQuery query) {
        return contractService.contractList(query);
    }

    /**
     * 合同预览（合同详情）
     *
     * @param id
     * @param response
     */
    @RequestMapping("/preview")
    public void contractView(@RequestParam("id") String id, HttpServletResponse response) {
        contractService.contractView(id, response);
    }

    /**
     * 合同下载
     *
     * @param id document_id
     * @return
     */
    @RequestMapping("/down")
    public BaseResult<?> contractDown(@RequestParam("id") String id) {
        return contractService.contractDown(id);
    }

}
