package com.example.live.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.example.live.entity.Anchor;
import com.example.live.entity.ResourceMerchant;
import com.example.live.mapper.AnchorMapper;
import com.example.live.mapper.ResourceMerchantMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * 读取excel文件
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/9 16:19
 */
@Component
public class ExcelUtil {

    @Autowired
    private AnchorMapper anchorMapper;
    @Autowired
    private ResourceMerchantMapper resourceMerchantMapper;

    /**
     * 主播excel上传处理
     * @param file
     */
    public void excelAnchorHandler(MultipartFile file) {
        ExcelListen<Anchor> readExcel = new ExcelListen<>();
        try {
            ExcelReader reader = EasyExcelFactory.getReader(file.getInputStream(), readExcel);
            reader.read(new Sheet(1, 1, Anchor.class));
            List<Anchor> anchors = readExcel.list;
            // 过滤异常数据
            anchors.removeIf(anchor -> StringUtils.isBlank(anchor.getNickname()));
            anchorMapper.batchIns(anchors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 商户资源
    public void excelResourceHandler(MultipartFile file) {
        ExcelListen<ResourceMerchant> readExcel = new ExcelListen<>();
        try {
            ExcelReader reader = EasyExcelFactory.getReader(file.getInputStream(), readExcel);
            reader.read(new Sheet(1, 1, ResourceMerchant.class));
            List<ResourceMerchant> merchants = readExcel.list;
            // 过滤异常数据
            merchants.removeIf(m -> StringUtils.isBlank(m.getMobile()));
            // 设置资源所属管理员（代理商）
            Integer userId = UserUtil.getUserId();
            merchants.forEach(m -> m.setAgentUser(userId));
            resourceMerchantMapper.batchIns(merchants);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
