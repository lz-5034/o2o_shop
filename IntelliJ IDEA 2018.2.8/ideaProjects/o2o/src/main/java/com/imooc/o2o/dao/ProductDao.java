package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/29 21:26:59
 * @description
 */
@Repository("productDao")
public interface ProductDao {
    //添加商品
    int insertProduct(Product product);

    //根据商品id返回商品完整信息
    Product selectProductById(long productId);

    //更新商品信息
    int updateProduct(Product product);

    //分页查询商品，通过条件进行筛选
    List<Product> selectProductList(@Param("product") Product product,
                                    @Param("rowIndex") int rowIndex,
                                    @Param("pageSize") int pageSize);

    //根据条件，返回查询商品的总数
    int selectProductCount(@Param("product") Product product);

    //删除 商品分类 前，把属于该商品分类下 所有商品 的商品分类项置为空
    int setProductCategoryToNull(long productCategoryId);
}
