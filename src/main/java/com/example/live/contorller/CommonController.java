package com.example.live.contorller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.service.UserService;
import com.example.live.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口：
 * 手机验证码发送、修改密码、重置密码
 * 文件上传：主播excel、商户资源excel、上传二维码、上传视频
 *
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 21:10
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private ExcelUtil excelUtil;
    @Autowired
    private UserService userService;

    /**
     * 验证码发送
     * @param mobile
     * @return
     */
    @GetMapping("/code")
    public BaseResult<?> mobileCode(@RequestParam("mobile") String mobile) {
        return userService.mobileCode(mobile);
    }

    /**
     * 验证码修改密码
     * @param jo mobile、code、pwd、source：back-管理端、merchant-商户端
     * @return
     */
    @PostMapping("/pwd/modify")
    public BaseResult<?> modifyPwd(@RequestBody JSONObject jo) {
        return userService.modifyPwd(jo);
    }



    /**
     * excel文件上传
     *
     * @param file
     * @param type 主播-anchor、商户资源-resource
     * @param param clear-清空数据上传
     * @return
     */
    @PostMapping(value = "/upload")
    public BaseResult<?> uploadAnchor(@RequestBody MultipartFile file
            , @RequestParam("type") String type, @RequestParam("param") String param) {
        if ("anchor".equals(type)) {
            excelUtil.excelAnchorHandler(file, param);
        } else if ("resource".equals(type)) {
            excelUtil.excelResourceHandler(file, param);
        }
        return new BaseResult<>();
    }
}
