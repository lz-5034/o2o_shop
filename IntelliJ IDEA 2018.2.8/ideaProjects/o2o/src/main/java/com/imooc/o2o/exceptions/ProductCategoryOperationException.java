package com.imooc.o2o.exceptions;

/**
 * @author LZ
 * @date 2020/2/21 21:45:50
 * @description
 */
public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = 1182563719599527969L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
