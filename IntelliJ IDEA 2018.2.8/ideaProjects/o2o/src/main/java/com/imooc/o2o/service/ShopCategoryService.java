package com.imooc.o2o.service;

import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/22 23:42:42
 * @description
 */
public interface ShopCategoryService {
    //定义常量作为Redis数据库的key
    String SHOP_CATEGORY_LIST_KEY = "shopcategorylist";

    List<ShopCategory> getShopCategory(ShopCategory shopCategory);
}
