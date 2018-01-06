package com.xym.sell.controller;

import com.xym.sell.dataobject.ProductCategory;
import com.xym.sell.dataobject.ProductInfo;
import com.xym.sell.service.CategoryService;
import com.xym.sell.service.ProductInfoService;
import com.xym.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVo<Page> list(){

        List<ProductInfo> productInfos = productInfoService.findUpAll();

        List<Integer> categoryList = new ArrayList<>();
        categoryService.findByCategoryTypeIn(categoryList);

        List<Integer> categoryTypeList = productInfos.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


        return null;
    }
}
