package com.xym.sell.service;

import com.xym.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    /**
     * 创建一个订单
     * @param orderDTO 订单DTO
     * @return 订单DTO
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 根据订单id查找订单
     * @param orderId 订单id
     * @return 订单DTO
     */
    OrderDTO findOne(String orderId);

    /**
     * 根据openid分页查找订单
     * @param buyerOpenid openid
     * @param pageable 分页参数
     * @return 分页订单数
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 分页查找订单
     * @param pageable 分页参数
     * @return 分页订单数
     */
    Page<OrderDTO> findList(Pageable pageable);

    OrderDTO cancel(OrderDTO orderDTO);

    OrderDTO finish(OrderDTO orderDTO);

    OrderDTO paid(OrderDTO orderDTO);

}
