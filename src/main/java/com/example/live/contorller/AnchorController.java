package com.example.live.contorller;

import com.example.live.common.BaseResult;
import com.example.live.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 主播列表
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 15:54
 */
@RestController
@RequestMapping("/anchor")
public class AnchorController {
    @Autowired
    private ExcelUtil excelUtil;

    /**
     * excel文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public BaseResult<?> uploadAnchor(@RequestBody() MultipartFile file) {
        excelUtil.excelAnchorHandler(file);
        return new BaseResult<>();
    }

}
