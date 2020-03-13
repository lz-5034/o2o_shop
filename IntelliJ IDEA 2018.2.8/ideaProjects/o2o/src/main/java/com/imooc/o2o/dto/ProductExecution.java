package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.enums.ProductStatusEnum;

import java.util.List;

/**
 * @author LZ
 * @date 2020/2/29 23:29:19
 * @description
 */
public class ProductExecution {
    private int status; //结果状态
    private String statusInfo;  //状态标识
    private int count;  //商品数量

    private Product product;    //增删改时候用
    private List<Product> productList;  //查询时候用

    public ProductExecution() {
    }
    //操作失败时使用的构造器
    public ProductExecution(ProductStatusEnum productStatusEnum) {
        this.status = productStatusEnum.getStatus();
        this.statusInfo = productStatusEnum.getStatusInfo();
    }
    //操作成功时使用的构造器
    public ProductExecution(ProductStatusEnum productStatusEnum, Product product) {
        this.status = productStatusEnum.getStatus();
        this.statusInfo = productStatusEnum.getStatusInfo();
        this.product = product;
    }
    //操作成功时使用的构造器
    public ProductExecution(ProductStatusEnum productStatusEnum, List<Product> productList) {
        this.status = productStatusEnum.getStatus();
        this.statusInfo = productStatusEnum.getStatusInfo();
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
