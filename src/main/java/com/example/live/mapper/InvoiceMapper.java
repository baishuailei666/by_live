package com.example.live.mapper;

import com.example.live.controller.query.InvoiceQuery;
import com.example.live.entity.Invoice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;
import com.example.live.vo.InvoiceVO;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:28
 */
@Mapper
public interface InvoiceMapper {

    @Insert("insert into invoice(merchant_id, ope_user, money, company, tax, email, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, #{money}, #{company}, #{tax}, #{email}, now(), now() )")
    void insInvoice(Invoice invoice);

    List<InvoiceVO> invoiceList(@Param("query") InvoiceQuery query, @Param("ids") List<Integer> ids);

    int invoiceListCount(@Param("query") InvoiceQuery query, @Param("ids") List<Integer> ids);

    @Update("update invoice set status=#{status},remark=#{remark},ut=now()  where id=#{id} ")
    void invoiceCheck(@Param("id")Integer id,@Param("status")Integer status,@Param("remark")String remark);

    @Select("select merchant_id as merchantId, ope_user as opeUser, money, company, tax, email, status, remark from invoice where id=#{id}")
    Invoice getInvoice(@Param("id") Integer id);

}
