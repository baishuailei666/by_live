package com.example.live.interceptor;

import com.example.live.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/8 23:16
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    // 提前注入bean，可以防止拦截器中无法注入bean
    @Resource
    private UserService userService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/user/**");

        // 添加新的拦截器
        registry.addInterceptor(new SysUserInterceptor(userService))
                .addPathPatterns(list);
    }

}
