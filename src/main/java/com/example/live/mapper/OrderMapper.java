package com.example.live.mapper;

import com.example.live.controller.query.OrderQuery;
import com.example.live.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:30
 */
@Mapper
public interface OrderMapper {

    @Insert("insert into order(order_no, merchant_id, buy_type, money, ope_user, pay_type, ct, ut)" +
            " values(#{orderNo}, #{merchantId}, #{buyType}, #{money}, #{opeUser}, #{payType}, now(), now())")
    void insOrder(Order order);

    @Select("select id, order_no as orderNo, trade_no as tradeNo, buy_type as buyType where `status`='TRADE_SUCCESS' and merchant_id=#{merchantId} order by ut desc limit 1")
    Order getOrder1(@Param("id") int merchantId);

    // 商户订单列表
    @Select("select id, order_no as orderNo, buy_type as buyType, money, pay_type as payType, ct, ut from `order`" +
            " where merchant_id=#{merchantId} and `status`='TRADE_SUCCESS'")
    List<Order> merchantOrderList(@Param("merchantId") Integer merchantId);

    int orderCount(@Param("query")OrderQuery query);

    List<Order> orderList(@Param("query")OrderQuery query);

    List<Order> orderListByUserId(@Param("query") OrderQuery query, @Param("userId") Integer userId,@Param("page") Integer page);

    int orderListByUserIdCount(@Param("query") OrderQuery query,@Param("userId") Integer userId);


}
