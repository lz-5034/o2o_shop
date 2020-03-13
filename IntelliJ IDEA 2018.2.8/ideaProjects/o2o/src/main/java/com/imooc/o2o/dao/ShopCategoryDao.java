package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/22 23:11:03
 * @description
 */
public interface ShopCategoryDao {
    List<ShopCategory> selectShopCategory(@Param("shopCategory") ShopCategory shopCategory);
}
