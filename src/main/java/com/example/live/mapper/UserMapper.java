package com.example.live.mapper;

import com.example.live.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id,remark,mobile,level,wx,ct FROM `user` where mobile=#{mobile} and pwd=#{pwd}")
    User getUser1(@Param("mobile") String mobile, @Param("pwd") String pwd);

    List<User> userList(@Param("agentUser") Integer agentUser, @Param("keyword") String keyword, @Param("page") int page);

    int count(@Param("agentUser") Integer agentUser, @Param("keyword") String keyword);
}
