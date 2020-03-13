package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

/**
 * @author 林仲
 * @date 2020/2/21 18:04:54
 * @description
 */
public interface ShopService {
    /**
     * 添加店铺
     * 为何传入一个输入流一个文件名，不直接传一整个CommonsMultipartFile？
     * 因为在unitTest测试的时候不容易构造出CommonsMultipartFile
     * @param shopImg   封装了图片文件流和文件名
     * @param shop  店铺其他基本信息
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(ImageHolder shopImg, Shop shop) throws ShopOperationException;

    /**
     * 修改店铺
     * @param shopImg   封装了图片文件流和文件名
     * @param shop  店铺其他基本信息
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(ImageHolder shopImg, Shop shop) throws ShopOperationException;

    /**
     * 通过店铺id获取店铺
     * @param shopId 店铺id
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 根据传入参数作为查询条件返回相应店铺列表
     * @param shop  作为查询条件
     * @param pageIndex 要查询的页码（注：不是数据库的查询行数）
     * @param pageSize  每页显示的数量
     * @return
     */
    ShopExecution getShopList(Shop shop, int pageIndex, int pageSize);
}
