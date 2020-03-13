package com.imooc.o2o.dto;

import com.imooc.o2o.entity.User;
import com.imooc.o2o.enums.UserStateEnum;

import java.util.List;

/**
 * 封装执行后结果
 */
public class UserExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的User（增删改用户的时候用）
	private User User;

	// 获取的User列表(查询用户列表的时候用)
	private List<User> UserList;

	public UserExecution() {
	}

	// 操作失败的构造器
	public UserExecution(UserStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 操作成功的构造器
	public UserExecution(UserStateEnum stateEnum, User User) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.User = User;
	}

	// 操作成功的构造器
	public UserExecution(UserStateEnum stateEnum, List<User> UserList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.UserList = UserList;
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

	public User getUser() {
		return User;
	}

	public void setUser(User User) {
		this.User = User;
	}

	public List<User> getUserList() {
		return UserList;
	}

	public void setUserList(List<User> UserList) {
		this.UserList = UserList;
	}

}