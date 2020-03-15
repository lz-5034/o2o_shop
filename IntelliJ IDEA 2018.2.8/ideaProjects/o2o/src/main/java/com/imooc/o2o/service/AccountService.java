package com.imooc.o2o.service;

import com.imooc.o2o.dto.AccountExecution;
import com.imooc.o2o.entity.Account;
import com.imooc.o2o.exceptions.AccountOperationException;

public interface AccountService {
	//通过帐号和密码获取平台帐号信息
	Account checkAccount(String userName, String password, int userType);

	//通过userId获取平台帐号信息
	Account getAccountByUserId(long userId);

	//绑定微信，生成平台专属的帐号
	AccountExecution bindAccount(Account Account) throws AccountOperationException;

	//修改平台帐号的登录密码
	AccountExecution modifyAccount(Long userId, String username, String password, String newPassword)
			throws AccountOperationException;
}
