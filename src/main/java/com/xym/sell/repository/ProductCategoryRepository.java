package com.xym.sell.repository;

import com.xym.sell.data.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>{

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);


}
