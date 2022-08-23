package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.service.CommonService;
import com.example.live.service.UserService;
import com.example.live.single.AsyncService;
import com.example.live.util.ExcelUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口：
 * 手机验证码发送、修改密码、重置密码
 * 文件上传：主播excel、商户资源excel、上传二维码、上传视频
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
    @Autowired
    private CommonService commonService;
    @Autowired
    private AsyncService asyncService;

    /**
     * 验证码发送
     *
     * @param mobile
     * @return
     */
    @GetMapping("/code")
    public BaseResult<?> mobileCode(@RequestParam("mobile") String mobile) {
        return userService.mobileCode(mobile);
    }

    /**
     * 验证码修改密码
     *
     * @param jo mobile、code、pwd、source：back-管理端、merchant-商户端
     * @return
     */
    @PostMapping("/pwd/modify")
    public BaseResult<?> modifyPwd(@RequestBody JSONObject jo) {
        return userService.modifyPwd(jo);
    }

    /**
     * 重置默认密码
     *
     * @param mobile 手机号
     * @param source source：back-管理端、merchant-商户端
     * @return
     */
    @GetMapping("/pwd/reset")
    public BaseResult<?> resetPwd(@RequestParam("mobile") String mobile, @RequestParam("source") String source) {
        return userService.resetPwd(mobile, source);
    }


    /**
     * 上传微信二维码
     *
     * @param file
     * @param id
     * @return
     */
    @PostMapping(value = "/upload/img")
    public BaseResult<?> uploadImg(@RequestBody MultipartFile file, @RequestParam("id") Integer id) {
        return userService.uploadImg(file, id);
    }

    /**
     * excel文件上传
     *
     * @param file
     * @param type  主播-anchor、商户资源-resource
     * @return
     */
    @PostMapping(value = "/upload/excel")
    public BaseResult<?> uploadExcel(@RequestBody MultipartFile file, @RequestParam("type") String type) {
        UserVO u = UserUtil.getUser();
        if (u!=null&&u.getId()== Constant.admin_id) {
            // 默认清空数据进行上传
            String param = "clear";
            if ("anchor".equals(type)) {
                excelUtil.excelAnchorHandler(file, param);
            } else if ("resource".equals(type)) {
                excelUtil.excelResourceHandler(file, param);
            }
        } else {
            return new BaseResult<>(17, "接口请求失败");
        }
        return new BaseResult<>();
    }

    /**
     * 视频上传
     *
     * @param file 视频文件
     * @return
     */
    @PostMapping("/upload/video")
    public BaseResult<?> uploadVideo(@RequestBody MultipartFile file) {
        return commonService.uploadVideo(file);
    }

    /**
     * 视频参数上传
     *
     * @return
     */
    @PostMapping("/upload/videoParam")
    public BaseResult<?> uploadVideoParam(@RequestBody JSONObject jo) {
        UserVO u = UserUtil.getUser();
        String cover = jo.getString("cover");//视频封面
        String path = jo.getString("path");//视频路径
        String title = jo.getString("title");//视频标题
        Integer level = jo.getInteger("level");//视频类型：全部-0、月卡-1、季卡-2、年卡-3
        if (StringUtils.isEmpty(path)||StringUtils.isEmpty(title)||level==null) {
            return new BaseResult<>(17, "参数错误，请重新填写");
        }
        if (u!=null&&u.getId()==Constant.admin_id) {
            return commonService.uploadVideoParam(path, title, level, cover);
        } else {
            return new BaseResult<>(17, "接口请求失败");
        }
    }

    /**
     * 通知中心
     *
     * @return
     */
    @GetMapping("/msg/center")
    public BaseResult<?> msgList(@RequestParam("page") Integer page) {
        return commonService.msgList(page);
    }

    /**
     * 消息通知：5s轮询
     *
     * @param source 后台-back、商户端-merchant
     * @return
     */
    @GetMapping("/msg")
    public BaseResult<?> notificationMsg(@RequestParam("source") String source) {
        return commonService.notificationMsg(source);
    }

    /**
     * 客服信息
     *
     * @return
     */
    @GetMapping("/kef")
    public BaseResult<?> kef() {
        return commonService.kef();
    }

    /**
     *  展示月卡、季卡、年卡价格
     * @return
     */
    @GetMapping("/price")
    public BaseResult<?> price(){
        return commonService.showPrices();
    }


    @GetMapping("/handler")
    public void handler(@RequestParam("tag") String tag){
        if ("1".equals(tag)) {
            asyncService.asyncResourceHandler();
        } else if ("2".equals(tag)) {
            try {
                Integer.parseInt("123.4test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
