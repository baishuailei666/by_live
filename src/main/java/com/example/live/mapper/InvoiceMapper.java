package com.example.live.mapper;

import com.example.live.entity.Invoice;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("insert into invoice(merchant_id, ope_user, money, company, tax, email, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, #{money}, #{company}, #{tax}, #{email}, now(), now() )")
    void insInvoice(Invoice invoice);


    List<InvoiceVo> invoiceList(@Param("query") InvoiceQuery query, @Param("userId") Integer userId);

    int invoiceListCount(@Param("query") InvoiceQuery query, @Param("userId") Integer userId);

    @Update("update invoice set status=#{status},remark=#{remark},ut=now()  where id=#{id} ")
    void invoiceUpdate(@Param("id")Integer id,@Param("status")Integer status,@Param("remark")String remark);

}
