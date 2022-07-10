package com.example.live.contorller;

import com.example.live.common.BaseResult;
import com.example.live.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传：主播excel、商户资源excel、图片二维码、视频
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 17:54
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private ExcelUtil excelUtil;

    /**
     * excel文件上传
     * @param file
     * @param type 主播-anchor、商户资源-resource
     * @return
     */
    @PostMapping(value = "/upload")
    public BaseResult<?> uploadAnchor(@RequestBody MultipartFile file, @RequestParam("type") String type) {
        if ("anchor".equals(type)) {
            excelUtil.excelAnchorHandler(file);
        } else if ("resource".equals(type)) {
            excelUtil.excelResourceHandler(file);
        }
        return new BaseResult<>();
    }


}
