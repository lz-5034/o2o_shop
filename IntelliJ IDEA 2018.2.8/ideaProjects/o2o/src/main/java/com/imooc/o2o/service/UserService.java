package com.imooc.o2o.service;

import com.imooc.o2o.dto.UserExecution;
import com.imooc.o2o.entity.User;

public interface UserService {

	//根据用户Id获取User信息
	User getUserById(Long userId);

	//根据查询条件分页返回用户信息列表
	UserExecution getUserList(User UserCondition, int pageIndex, int pageSize);

	//根据传入的User修改对应的用户信息
	UserExecution modifyUser(User pi);
}
