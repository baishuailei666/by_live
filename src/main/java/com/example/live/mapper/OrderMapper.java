package com.example.live.mapper;

import com.example.live.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:30
 */
@Mapper
public interface OrderMapper {

    @Select("select id, order_no as orderNo, trade_no as tradeNo, buy_type as buyType where `status`='TRADE_SUCCESS' and merchant_id=#{merchantId} order by ut desc limit 1")
    Order getOrder1(@Param("id") int merchantId);
}
