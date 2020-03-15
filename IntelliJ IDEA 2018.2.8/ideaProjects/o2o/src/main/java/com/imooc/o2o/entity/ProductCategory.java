package com.imooc.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/18 11:18:32
 * @description 商品类别
 */
@Data
public class ProductCategory {
    private Long productCategoryId; //商品类别id
    private String productCategoryName; //商品分类名称
    private Integer priority;       //优先级
    private Date createTime;        //创建时间
    private Long shopId;            //店铺id

    /*public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }*/
}
