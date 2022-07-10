package com.example.live.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Chen Rui
 * @Date 2022/7/10 21:46
 * @Description
 */
@Data
@AllArgsConstructor
public class Page<T> {

    //内容
    private T records;

    //总数
    private Integer total;

    //每页展示数
    private Integer size;

    //当前页
    private Integer current;

    //总页数
    private Integer pages;

}
