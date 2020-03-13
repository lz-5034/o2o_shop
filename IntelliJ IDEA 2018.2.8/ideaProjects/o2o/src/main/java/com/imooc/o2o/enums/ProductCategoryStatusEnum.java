package com.imooc.o2o.enums;

/**
 * @author 林仲
 * @date 2020/2/28 21:52:47
 * @description
 */
public enum ProductCategoryStatusEnum {
    SUCCESS(1,"创建成功"),INNER_ERROR(-1001,"操作失败"), EMPTY_LIST(-1002, "添加数少于1");

    private int status;
    private String statusInfo;

    ProductCategoryStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public static ProductCategoryStatusEnum statusOf(int status) {
        for (ProductCategoryStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return null;
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
}
