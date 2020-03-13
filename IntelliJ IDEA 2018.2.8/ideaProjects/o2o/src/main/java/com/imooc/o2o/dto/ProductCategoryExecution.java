package com.imooc.o2o.dto;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStatusEnum;

import java.util.List;

/**
 * @author LZ
 * @date 2020/2/28 21:38:24
 * @description
 */
public class ProductCategoryExecution {
    private int status; //结果状态
    private String statusInfo;  //状态标识
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(ProductCategoryStatusEnum statusEnum) {
        this.status = statusEnum.getStatus();
        this.statusInfo = statusEnum.getStatusInfo();
    }

    public ProductCategoryExecution(ProductCategoryStatusEnum statusEnum, List<ProductCategory> productCategoryList) {
        this.status = statusEnum.getStatus();
        this.statusInfo = statusEnum.getStatusInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
