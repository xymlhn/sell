package com.xym.sell.service.impl;

import com.xym.sell.data.OrderDetail;
import com.xym.sell.dto.OrderDTO;
import com.xym.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "123";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("jason");
        orderDTO.setBuyerPhone("15521314612");
        orderDTO.setBuyerAddress("fuck difang");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123");
        orderDetail.setProductQuantity(2);
        orderDetails.add(orderDetail);

        orderDTO.setOrderDetails(orderDetails);
        OrderDTO result = orderService.create(orderDTO);
        log.info("订单创建 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}