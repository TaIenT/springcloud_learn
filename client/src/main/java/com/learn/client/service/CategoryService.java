package com.learn.client.service;

import com.learn.client.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 */


public interface CategoryService {


    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
