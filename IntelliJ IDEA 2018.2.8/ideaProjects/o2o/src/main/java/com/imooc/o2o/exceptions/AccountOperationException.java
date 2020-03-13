package com.imooc.o2o.exceptions;

/**
 * @author LZ
 * @date 2020/2/21 21:45:50
 * @description
 */
public class AccountOperationException extends RuntimeException {

	private static final long serialVersionUID = -8260236137099919700L;

	public AccountOperationException(String msg) {
		super(msg);
	}
}
