package com.imooc.o2o.enums;

/**
 * @author 林仲
 * @date 2020/2/21 16:56:48
 * @description
 */
public enum ShopStatusEnum {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"shopId为空"),
    NULL_SHOP(-1003,"shop信息为空");

    private int status;
    private String statusInfo;

    ShopStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public static ShopStatusEnum statusOf(int status) {
        for (ShopStatusEnum shopStatusEnum : values()) {
            if (shopStatusEnum.getStatus() == status) {
                return shopStatusEnum;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

}
