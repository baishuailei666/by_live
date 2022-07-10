package com.example.live.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源商户
 * 备注：【已处理】的资源不进行二次分配；定期清理（30天）
 *  系统每天0点进行资源分配；
 *  【已拒绝】【未联系】会重新进行分配
 *  根据资源池数量和业务员数量进行自动分配
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceMerchant extends BaseRowModel {
    @ExcelProperty(value = "序号",index = 0)
    private int id;
    @ExcelProperty(value = "店铺名称",index = 1)
    private String shop;
    // 手机号
    @ExcelProperty(value = "手机号",index = 2)
    private String mobile;
    // 管理员（根据当前上传的user)
    private Integer agentUser;
    // 意向程度：未联系-0、跟进中-1、已处理-2
    private int intention;
    // 业务员
    private Integer opeUser;
    // 创建时间
    private String ct;
    // 更新时间
    private String ut;
}
