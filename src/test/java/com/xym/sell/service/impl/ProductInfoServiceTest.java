package com.xym.sell.service.impl;

import com.xym.sell.data.ProductInfo;
import com.xym.sell.enums.ProductStatusEnum;
import com.xym.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("傻逼粥1");
        productInfo.setProductPrice(new BigDecimal(3.12));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("傻逼喝的粥1");
        productInfo.setProductIcon("http://..jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(10);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo result = productInfoService.onSale("1");
        Assert.assertNotEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }

    @Test
    public void offSale(){
        ProductInfo result = productInfoService.offSale("1");
        Assert.assertNotEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }

}