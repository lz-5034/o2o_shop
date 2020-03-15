package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AccountDao;
import com.imooc.o2o.dto.AccountExecution;
import com.imooc.o2o.entity.Account;
import com.imooc.o2o.enums.AccountStateEnum;
import com.imooc.o2o.exceptions.AccountOperationException;
import com.imooc.o2o.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao AccountDao;

	@Override
	public Account checkAccount(String username, String password, int userType) {
		return AccountDao.selectAccountByNameAndPwdAndType(username, password, userType);
	}

	@Override
	public Account getAccountByUserId(long userId) {
		return AccountDao.selectAccountByUserId(userId);
	}

	@Override
	@Transactional
	public AccountExecution bindAccount(Account Account) throws AccountOperationException {
		// 空值判断，传入的Account 帐号密码，用户信息特别是userId不能为空，否则直接返回错误
		if (Account == null || Account.getPassword() == null || Account.getUsername() == null
				|| Account.getUser() == null || Account.getUser().getUserId() == null) {
			return new AccountExecution(AccountStateEnum.NULL_AUTH_INFO);
		}
		// 查询此用户是否已绑定过平台帐号
		Account tempAuth = AccountDao.selectAccountByUserId(Account.getUser().getUserId());
		if (tempAuth != null) {
			// 如果绑定过则直接退出，以保证平台帐号的唯一性
			return new AccountExecution(AccountStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			// 如果之前没有绑定过平台帐号，则创建一个平台帐号与该用户绑定
			Account.setCreateTime(new Date());
			Account.setLastEditTime(new Date());
			Account.setPassword(Account.getPassword());
			int effectedNum = AccountDao.insertAccount(Account);
			// 判断创建是否成功
			if (effectedNum <= 0) {
				throw new AccountOperationException("帐号绑定失败");
			} else {
				return new AccountExecution(AccountStateEnum.SUCCESS, Account);
			}
		} catch (Exception e) {
			throw new AccountOperationException("insertAccount error: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public AccountExecution modifyAccount(Long userId, String userName, String password, String newPassword)
			throws AccountOperationException {
		// 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
		if (userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				// 更新密码
				int effectedNum = AccountDao.updateAccount(userId, userName, password,
						newPassword, new Date());
				// 判断更新是否成功
				if (effectedNum <= 0) {
					throw new AccountOperationException("更新密码失败");
				}
				return new AccountExecution(AccountStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new AccountOperationException("更新密码失败:" + e.toString());
			}
		} else {
			return new AccountExecution(AccountStateEnum.NULL_AUTH_INFO);
		}
	}

}
