package com.example.live.service;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.BaseResult;

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

    BaseResult<?> userCreate(JSONObject jo);

    BaseResult<?> userDel(Integer id);

}
