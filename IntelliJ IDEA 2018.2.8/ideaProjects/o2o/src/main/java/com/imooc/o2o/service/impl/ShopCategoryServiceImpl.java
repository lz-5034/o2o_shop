package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryOperationException;
import com.imooc.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/22 23:46:10
 * @description 店铺分类，引入Redis
 */
@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    //日志
    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    /**
     * 根据传来的条件，返回店铺分类集合
     * @param shopCategory
     * @return
     */
    @Override
    public List<ShopCategory> getShopCategory(ShopCategory shopCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ShopCategory> shopCategoryList = null;
        String key = SHOP_CATEGORY_LIST_KEY;
        //根据不同的查询条件，拼接不同的key
        if (shopCategory == null) {
            //若查询条件为空，则是列出首页的店铺分类，即parentId为空的店铺类别
            key = key + "_allparent";
        } else if (shopCategory != null && shopCategory.getParent() != null && shopCategory.getParent().getShopCategoryId() != null) {
            //父店铺类别id不为空，查出该父店铺类别id下的所有子店铺类别，即指定父id下的所有二级店铺类别
            key = key + "_parent"+shopCategory.getParent().getShopCategoryId();
        } else if (shopCategory != null) {
            //只传过来一个对象，里面没有值，则是查找所有的二级类别
            key = key + "_allchild";
        }
        try {
            //当不存在该key时
            if (!jedisKeys.exists(key)) {
                shopCategoryList = shopCategoryDao.selectShopCategory(shopCategory);
                String value = objectMapper.writeValueAsString(shopCategoryList);
                jedisStrings.set(key, value);
            } else {    //当该key存在时
                String value = jedisStrings.get(key);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                shopCategoryList = objectMapper.readValue(value, javaType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new ShopCategoryOperationException(e.toString());
        }
        return shopCategoryList;
    }
}
