package com.example.live.mapper;

import com.example.live.entity.User;
import org.apache.ibatis.annotations.*;

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

    @Update("update `user` set pwd=#{pwd} where mobile=#{mobile}")
    void modifyPwd(@Param("mobile") String mobile, @Param("pwd") String pwd);

    @Insert("insert into `user`(mobile, pwd, level, remark) values(#{mobile}, #{level}, #{remark})")
    void insUser(@Param("mobile") String mobile, @Param("pwd") String pwd, @Param("level") Integer level, @Param("remark") String remark);

    @Update("update `user` set wx=#{wx} where id=#{id}")
    void updateImg(@Param("wx") String wx, @Param("id") Integer id);

    @Select("SELECT id,remark,mobile,level,wx,ct FROM `user` where mobile=#{mobile}")
    User getUserMobile(@Param("mobile") String mobile);

    @Select("select LAST_INSERT_ID()")
    int lastId();

    @Delete("delete from `user` where id=#{id}")
    void delUser(@Param("id") Integer id);

    // 超级管理员-1、管理员（代理）-2
    @Select("select id from `user` where level in (1, 2)")
    List<Integer> levelUser();


}
