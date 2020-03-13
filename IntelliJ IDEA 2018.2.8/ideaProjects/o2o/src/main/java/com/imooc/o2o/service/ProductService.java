package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/29 23:39:35
 * @description
 */
public interface ProductService {
    /**
     * 商品添加功能
     * @param thumbnail 缩略图
     * @param productImgList 详情图集合
     * @param product 商品的基本信息
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(ImageHolder thumbnail, List<ImageHolder> productImgList, Product product) throws ProductOperationException;

    /**
     * 根据商品id返回完整商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 商品修改功能
     * @param thumbnail 缩略图
     * @param productImgList 详情图集合
     * @param product 商品的基本信息
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(ImageHolder thumbnail, List<ImageHolder> productImgList, Product product) throws ProductOperationException;

    /**
     * 根据查询条件返回商品集合，分页显示
     * @param product
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product product, int pageIndex, int pageSize);

}
