package com.xym.sell.service;

import com.xym.sell.DO.ProductCategory;

import java.util.List;

public interface CategoryService {

    /**
     * 根据类目id查找类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查找所有类目
     * @return
     */
    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
