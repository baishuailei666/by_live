package com.example.live.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.example.live.entity.Anchor;
import com.example.live.mapper.AnchorMapper;
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

    /**
     * excel上传处理
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

    public void readExcelHandler() throws FileNotFoundException {
        String file1 = "C:\\Users\\baide0328\\Desktop\\主播上传模板.xlsx";
        FileInputStream fis = new FileInputStream(file1);
        InputStream is = new BufferedInputStream(fis);
        ExcelListen<Anchor> readExcel = new ExcelListen<>();
        ExcelReader reader = EasyExcelFactory.getReader(is, readExcel);
        reader.read(new Sheet(1, 1, Anchor.class));
        List<Anchor> anchors = readExcel.list;
        anchors.removeIf(anchor -> StringUtils.isBlank(anchor.getNickname()));
        System.out.println("anchors:"+anchors.size());

        anchorMapper.batchIns(anchors);

    }
}
