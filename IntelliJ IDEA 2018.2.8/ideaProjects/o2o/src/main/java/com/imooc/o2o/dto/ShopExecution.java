package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStatusEnum;

import java.util.List;

/**
 * @author LZ
 * @date 2020/2/21 16:45:15
 * @description
 */
public class ShopExecution {
    //结果状态
    private int status;
    //状态标识
    private String statusInfo;
    //店铺数量
    private int count;
    //操作的shop（增删改店铺的时候用到）
    private Shop shop;
    //shop列表（查询店铺列表的时候用到）
    private List<Shop> shopList;

    public ShopExecution() {
    }

    //店铺操作失败用到的构造器
    public ShopExecution(ShopStatusEnum shopStatusEnum) {
        this.status = shopStatusEnum.getStatus();
        this.statusInfo = shopStatusEnum.getStatusInfo();
    }
    //店铺操作成功用到的构造器
    public ShopExecution(ShopStatusEnum shopStatusEnum, Shop shop) {
        this.status = shopStatusEnum.getStatus();
        this.statusInfo = shopStatusEnum.getStatusInfo();
        this.shop = shop;
    }
    //店铺操作成功用到的构造器
    public ShopExecution(ShopStatusEnum shopStatusEnum, List<Shop> shopList) {
        this.status = shopStatusEnum.getStatus();
        this.statusInfo = shopStatusEnum.getStatusInfo();
        this.shopList = shopList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
