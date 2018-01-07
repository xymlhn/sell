package com.xym.sell.service;

import com.xym.sell.dto.OrderDTO;

public interface PayService {

    void create(OrderDTO orderDTO);

}
