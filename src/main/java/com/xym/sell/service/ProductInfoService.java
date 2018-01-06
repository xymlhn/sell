package com.xym.sell.service;

import com.xym.sell.data.ProductInfo;
import com.xym.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductInfoService {
    ProductInfo findOne(String productId);

    /*查询所有在架列表*/
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOS);

    //减库存

    void decreaseStock(List<CartDTO> cartDTOS);


}
