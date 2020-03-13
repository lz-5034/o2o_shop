package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/26 11:42:35
 * @description
 */
@Repository("productCategoryDao")
public interface ProductCategoryDao {
    /**
     * 根据店铺id查询出该店铺下所有商品分类
     * @param shopId
     * @return
     */
    List<ProductCategory> selectProductCategoryList(long shopId);

    /**
     * 批量添加商品分类
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 根据商品分类id和店铺id删除指定商品分类
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
