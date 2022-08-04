package com.example.live.controller;

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
     *
     * @param query page、mobile、shop、company
     * @return
     */
    @PostMapping("/list")
    public BaseResult<?> contractList(@RequestBody ContractQuery query) {
        return contractService.contractList(query);
    }

    /**
     * 合同详情
     *
     * @param id document_id
     * @return
     */
    @GetMapping("/info")
    public BaseResult<?> contractInfo(@RequestParam("id") String id) {
        return contractService.contractInfo(id);
    }

//  todo 明天搞
//    @Autowired
//    private CloudSignUtil cloudSignUtil;
//
//    /**
//     * 合同预览
//     * @param id
//     * @param response
//     */
//    public void contractView(@RequestParam("id") String id, HttpServletResponse response) {
//        String url = cloudSignUtil.signDown(id);
//        long timeMillis = System.currentTimeMillis();
//        //临时文件路径
//        File path = Constant.getCurrentPath(Constant.TEMPORARY);
//        FileWriter fileWriter;
//        try {
//            fileWriter = new FileWriter(path + Constant.separator() + timeMillis + ".pdf", false);
//            fileWriter.write(data);
//            fileWriter.close();
//        } catch (IOException e) {
//            log.error("文件输出有误。");
//        }
//
//        byte[] read = null;
//        try {
//            read = FileUtils.readFileToByteArray(new File(path + Constant.separator() + timeMillis + ".txt"));
//        } catch (IOException e) {
//            log.error("文件读取有误。");
//        }
//        // 删除临时文件
//        FileUtils.delete(path);
//
//        // 将文件内容 byte[]，通过 response 返回给客户端进行下载
//        if (read != null && read.length > 0) {
//            try {
////                response.setContentType("application/octet-stream");
//                response.setContentType("application/txt");
//                response.setCharacterEncoding("utf-8");
//                response.setHeader("Content-disposition", "attachment; filename=json.txt");
//                response.setHeader("Content-Length", String.valueOf(read.length));
//                // 这里URLEncoder.encode可以防止中文乱码
//                String fileName = URLEncoder.encode("季度报告.txt", "UTF-8").replaceAll("\\+", "%20");
//                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//                response.getOutputStream().write(read);
//                response.flushBuffer();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

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
