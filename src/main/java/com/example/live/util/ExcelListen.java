package com.example.live.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 读取excel文件
 * @param <T>
 */
public class ExcelListen<T extends BaseRowModel> extends AnalysisEventListener<T> {

    List<T> list = Lists.newArrayList();

    // 一行一行去读取Excel中的内容，即这个方法每一条数据解析都会来调用
    @Override
    public void invoke(T object, AnalysisContext context) {
        list.add(object);
    }

    // 读取内容完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("#excel list:"+list.size());
    }

}
