package com.example.live.controller;

import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.ContractQuery;
import com.example.live.service.ContractService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.UserVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    @Autowired
    private CloudSignUtil cloudSignUtil;

    /**
     * 合同列表
     *
     * @param query page、mobile、shop、company
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> contractList(@RequestBody ContractQuery query) {
        return contractService.contractList(query);
    }

    /**
     * 合同预览（合同详情）
     *
     * @param id
     * @param response
     */
    @GetMapping("/preview")
    public void contractView(@RequestParam("id") String id, HttpServletResponse response) {
        contractService.contractView(id, response);
    }

    /**
     * 合同下载
     *
     * @param id document_id
     * @return
     */
    @GetMapping("/down")
    public BaseResult<?> contractDown(@RequestParam("id") String id) {
        return contractService.contractDown(id);
    }

}
