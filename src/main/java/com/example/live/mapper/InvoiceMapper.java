package com.example.live.mapper;

import com.example.live.controller.query.InvoiceQuery;
import com.example.live.vo.InvoiceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:28
 */
@Mapper
public interface InvoiceMapper {

    List<InvoiceVo> invoiceList(@Param("query") InvoiceQuery query, @Param("userId") Integer userId);

    int invoiceListCount(@Param("query") InvoiceQuery query, @Param("userId") Integer userId);

    @Update("update invoice set status=#{status},remark=#{remark},ut=now()  where id=#{id} ")
    void invoiceUpdate(@Param("id")Integer id,@Param("status")Integer status,@Param("remark")String remark);

}
