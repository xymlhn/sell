package com.xym.sell.service;

import com.xym.sell.dto.OrderDTO;

public interface BuyService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}

