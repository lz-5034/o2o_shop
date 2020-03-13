package com.imooc.o2o.exceptions;

/**
 * @author LZ
 * @date 2020/2/21 21:45:50
 * @description
 */
public class ShopCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = 5423986306645291467L;

    public ShopCategoryOperationException(String msg) {
        super(msg);
    }
}
