package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.UserDao;
import com.imooc.o2o.dto.UserExecution;
import com.imooc.o2o.entity.User;
import com.imooc.o2o.enums.UserStateEnum;
import com.imooc.o2o.exceptions.UserOperationException;
import com.imooc.o2o.service.UserService;
import com.imooc.o2o.utils.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao UserDao;

	@Override
	public User getUserById(Long userId) {
		return UserDao.queryUserById(userId);
	}

	@Override
	public UserExecution getUserList(User UserCondition, int pageIndex, int pageSize) {
		UserExecution se = new UserExecution();
		try {
			if (UserCondition != null && pageIndex > 0 && pageSize > 0) {
				// 将传递过来的页码转换成分页查询的行数
				int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
				// 获取用户信息列表
				List<User> UserList = UserDao.queryUserList(UserCondition, rowIndex, pageSize);
				int count = UserDao.queryUserCount(UserCondition);
				if (UserList != null) {
					se.setUserList(UserList);
					se.setCount(count);
					se.setState(UserStateEnum.SUCCESS.getState());
				} else {
					se.setState(UserStateEnum.INNER_ERROR.getState());
				}
			} else {
				throw new UserOperationException("empty user or pageIndex or pageSize");
			}
		} catch (UserOperationException e) {
			e.printStackTrace();
			se.setState(UserStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	@Transactional
	public UserExecution modifyUser(User User) {
		// 空值判断，主要是判断用户Id是否为空
		if (User == null || User.getUserId() == null) {
			return new UserExecution(UserStateEnum.EMPTY);
		} else {
			try {
				// 更新用户信息
				int effectedNum = UserDao.updateUser(User);
				if (effectedNum <= 0) {
					return new UserExecution(UserStateEnum.INNER_ERROR);
				} else {
					User = UserDao.queryUserById(User.getUserId());
					return new UserExecution(UserStateEnum.SUCCESS, User);
				}
			} catch (Exception e) {
				throw new UserOperationException("updateUser error: " + e.getMessage());
			}
		}
	}
}
