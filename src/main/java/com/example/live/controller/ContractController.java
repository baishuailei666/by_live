package com.example.live.controller;

import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.ContractQuery;
import com.example.live.service.ContractService;
import com.example.live.util.CloudSignUtil;
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


    @Autowired
    private CloudSignUtil cloudSignUtil;


    /**
     * 合同预览（合同详情）
     *
     * @param id
     * @param response
     */
    @GetMapping("/preview")
    public void contractView(@RequestParam("id") String id, HttpServletResponse response) {
        //获取合同下载url
        String pdfUrl = cloudSignUtil.signDown(id);
        long timeMillis = System.currentTimeMillis();
        //临时文件夹路径
        File path = Constant.getCurrentPath(Constant.TEMPORARY);
        //文件路径
        String filePath = path + Constant.separator() + timeMillis + ".pdf";
        try {
            URL url = new URL(pdfUrl);
            File file = new File(filePath);
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保存文件出错");
        }

        //读取文件
        byte[] read = null;
        try {
            read = FileUtils.readFileToByteArray(new File(filePath));
        } catch (IOException e) {
            System.out.println("文件读取有误");
        }
        // 将文件内容 byte[]，通过 response 返回给客户端进行下载
        if (read != null && read.length > 0) {
            try {
                response.setContentType("application/pdf");
                response.setHeader("Content-Length", String.valueOf(read.length));
                response.getOutputStream().write(read);
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    FileUtils.forceDelete(new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
