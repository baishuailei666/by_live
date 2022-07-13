package com.example.live.mapper;

import com.example.live.entity.Invoice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:28
 */
@Mapper
public interface InvoiceMapper {

    @Insert("insert into invoice(merchant_id, ope_user, money, company, tax, email, ct, ut)" +
            "values(#{merchantId}, #{opeUser}, #{money}, #{company}, #{tax}, #{email}, now(), now() )")
    void insInvoice(Invoice invoice);

}
