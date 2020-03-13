package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStatusEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/26 11:54:27
 * @description
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 通过店铺id获取所有商品分类
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.selectProductCategoryList(shopId);
    }

    /**
     * 批量添加商品分类
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        try {
            //传入商品分类集合有值的情况：
            if (productCategoryList != null && productCategoryList.size() > 0) {
                //给所有商品分类添加创建时间
                for (ProductCategory productCategory : productCategoryList) {
                    productCategory.setCreateTime(new Date());
                }
                //商品分类批量插入操作
                int i = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (i <= 0) {
                    throw new ProductCategoryOperationException("商品分类添加失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS);
                }
            } else {    //集合为空：
                return new ProductCategoryExecution(ProductCategoryStatusEnum.EMPTY_LIST);
            }
        } catch (ProductCategoryOperationException e) {
            throw new ProductCategoryOperationException("batchAddProductCategory error : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException{
        try {
            //将关联商品的商品分类置为空
            int effectedNum = productDao.setProductCategoryToNull(productCategoryId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败！");
            }
            //删除该商品分类
            int i = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (i <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败！");
            } else {
                return new ProductCategoryExecution(ProductCategoryStatusEnum.SUCCESS);
            }
        } catch (ProductCategoryOperationException e) {
            throw new ProductCategoryOperationException("deleteProductCategory error : " + e.getMessage());
        }
    }
}
