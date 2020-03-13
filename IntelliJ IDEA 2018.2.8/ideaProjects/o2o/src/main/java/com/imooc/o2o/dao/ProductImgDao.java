package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/29 21:35:32
 * @description
 */
@Repository("productImgDao")
public interface ProductImgDao {
    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(long productId);

    List<ProductImg> selectProductImgByProductId(long productId);
}
