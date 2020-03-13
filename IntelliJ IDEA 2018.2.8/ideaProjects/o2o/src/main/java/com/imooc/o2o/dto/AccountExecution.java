package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Account;
import com.imooc.o2o.enums.AccountStateEnum;

import java.util.List;

public class AccountExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	private int count;

	private Account Account;

	private List<Account> AccountList;

	public AccountExecution() {
	}

	// 操作失败的构造器
	public AccountExecution(AccountStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 操作成功的构造器
	public AccountExecution(AccountStateEnum stateEnum, Account Account) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.Account = Account;
	}

	// 操作成功的构造器
	public AccountExecution(AccountStateEnum stateEnum,
                              List<Account> AccountList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.AccountList = AccountList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Account getAccount() {
		return Account;
	}

	public void setAccount(Account Account) {
		this.Account = Account;
	}

	public List<Account> getAccountList() {
		return AccountList;
	}

	public void setAccountList(List<Account> AccountList) {
		this.AccountList = AccountList;
	}

}
