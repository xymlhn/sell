package com.xym.sell.repository;

import com.xym.sell.DO.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123");
        orderDetail.setOrderId("1111");
        orderDetail.setProductId("123");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductName("fuck");
        orderDetail.setProductPrice(new BigDecimal(2.3));
        orderDetail.setProductQuantity(2);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = repository.findByOrderId("1111");
        Assert.assertNotEquals(0,orderDetails.size());
    }
}