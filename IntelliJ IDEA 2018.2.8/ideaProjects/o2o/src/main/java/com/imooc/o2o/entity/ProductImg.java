package com.imooc.o2o.entity;

import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/18 11:35:28
 * @description 商品图片详情
 */
public class ProductImg {
    private Long imgId;     //商品图片id
    private String imgAddr; //商品图片地址
    private String imgDesc; //商品图片描述
    private Integer priority;   //优先级
    private Date createTime;    //创建时间
    private Long productId; //关联商品id

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
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
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
