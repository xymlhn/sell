package com.xym.sell.service.impl;

import com.xym.sell.data.ProductInfo;
import com.xym.sell.dto.CartDTO;
import com.xym.sell.enums.ProductStatusEnum;
import com.xym.sell.enums.ResultEnum;
import com.xym.sell.exception.SellException;
import com.xym.sell.repository.ProductInfoRepository;
import com.xym.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(org.springframework.data.domain.Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer number = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(number);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDto : cartDTOS) {
            ProductInfo productInfo = repository.findOne(cartDto.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer number = productInfo.getProductStock() - cartDto.getProductQuantity();

            if (number < 0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }else {
                productInfo.setProductStock(number);
                repository.save(productInfo);
            }

        }
    }
}
