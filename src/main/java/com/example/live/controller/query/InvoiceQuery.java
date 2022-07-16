package com.example.live.controller.query;

import lombok.Data;

import java.util.List;

/**
 * @Author Chen Rui
 * @Date 2022/7/13 20:05
 * @Description
 */
@Data
public class InvoiceQuery {

    private String mobile;
    private Integer status;
    private Integer page;
    private String shop;
    // ope_user
    private List<Integer> ids;

}
