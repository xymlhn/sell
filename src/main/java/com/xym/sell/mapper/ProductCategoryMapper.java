package com.xym.sell.mapper;

import com.xym.sell.DO.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface ProductCategoryMapper {
    ProductCategory findByCategoryType(@Param("categoryType") Integer categoryType);
}
