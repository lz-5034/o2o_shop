package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/20 21:30:24
 * @description
 */
@Repository("shopDao")
public interface ShopDao {
    //新增店铺
    int insertShop(Shop shop);

    //更新店铺信息
    int updateShop(Shop shop);

    //通过店铺id查询店铺
    Shop selectByShopId(long shopId);

    //分页查询店铺，通过条件进行筛选
    List<Shop> selectShopList(@Param("shop") Shop shop,
                              @Param("rowIndex") int rowIndex,
                              @Param("pageSize") int pageSize);

    //为selectShopList方法提供查询总数
    Integer selectShopListCount(@Param("shop") Shop shop);
}
