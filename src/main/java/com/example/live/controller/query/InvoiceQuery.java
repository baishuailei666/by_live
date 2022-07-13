package com.example.live.controller.query;

import lombok.Data;

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
    private Integer size;
    private String shop;
    private Integer index;

    public Integer getIndex(){
        return (this.page - 1) * size;
    }

}
