package com.example.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息通知
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/9 16:34
 */
@RestController
@RequestMapping("/msg")
public class MsgController {


    /**
     * 服务端向客户端推送消息
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/pushToWeb")
    public BaseResult<?> pushToWeb(@RequestBody JSONObject jsonObject) {
        long ts = System.currentTimeMillis();
        WebSocketServer.sendInfo("当前时间:"+ts, "12");
        return new BaseResult<>(200, "success", "123456");
    }

}
