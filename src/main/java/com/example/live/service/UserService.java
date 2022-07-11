package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;
import com.example.live.contorller.query.OrderQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
public interface UserService {

    BaseResult<?> userLogin(HttpSession session, JSONObject jo);

    BaseResult<?> userList(String keyword, Integer page);

    BaseResult<?> mobileCode(String mobile);

    BaseResult<?> modifyPwd(JSONObject jo);

    BaseResult<?> resetPwd(String mobile, String source);

    BaseResult<?> uploadImg(MultipartFile file, Integer id);

    BaseResult<?> userCreate(JSONObject jo);

    BaseResult<?> userDel(Integer id);

    BaseResult<?> orderList(OrderQuery query);

}
