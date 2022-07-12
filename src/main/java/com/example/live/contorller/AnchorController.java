package com.example.live.contorller;

import com.example.live.common.BaseResult;
import com.example.live.service.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private AnchorService anchorService;


    /**
     * 主播详情
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    public BaseResult<?> anchorInfo(@RequestParam("id") Integer id) {
        return anchorService.anchorInfo(id);
    }

    /**
     * 商户端主播列表
     *
     * @param category
     * @param page     1
     * @return
     */
    @GetMapping("/list")
    public BaseResult<?> anchorMerchantList(@RequestParam("category") String category, @RequestParam("page") Integer page) {
        return anchorService.anchorList();
    }

    /**
     * 商户端主播已查看
     *
     * @param keyword 昵称搜索
     * @param sort    排序 橱窗销量-sales、账号等级-level、
     *                直播天数-live_day、推广数量-goods、转化指数-rate
     * @return
     */
    @GetMapping("/collect")
    public BaseResult<?> anchorMerchantCollect(@RequestParam("keyword") String keyword, @RequestParam("sort") String sort) {
        return anchorService.anchorCollect();
    }

    /**
     * 已加微信
     *
     * @param id
     * @return
     */
    @GetMapping("/addWx")
    public BaseResult<?> anchorAddWx(@RequestParam("id") Integer id) {
        return anchorService.anchorAddWx(id);
    }

    /**
     * 移除已查看主播
     *
     * @param id
     * @return
     */
    @GetMapping("/remove")
    public BaseResult<?> anchorRemove(@RequestParam("id") Integer id) {
        return anchorService.anchorRemove(id);
    }

    /**
     * 跟进记录
     *
     * @param id
     * @return
     */
    @GetMapping("/follow")
    public BaseResult<?> anchorFollow(@RequestParam("id") Integer id) {
        return anchorService.anchorRemove(id);
    }

    /**
     * 跟进记录添加
     *
     * @param id
     * @param content
     * @return
     */
    @GetMapping("/follow/add")
    public BaseResult<?> anchorFollowAdd(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        return anchorService.anchorRemove(id);
    }


}
