package com.imooc.o2o.exceptions;

/**
 * @author LZ
 * @date 2020/2/21 21:45:50
 * @description
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 2361446884822298905L;

    public ShopOperationException(String msg) {
        super(msg);
    }
}
