package com.imooc.o2o.exceptions;

/**
 * @author LZ
 * @date 2020/2/21 21:45:50
 * @description
 */
public class UserOperationException extends RuntimeException {

    private static final long serialVersionUID = -2455400735135407580L;

    public UserOperationException(String msg) {
        super(msg);
    }
}
