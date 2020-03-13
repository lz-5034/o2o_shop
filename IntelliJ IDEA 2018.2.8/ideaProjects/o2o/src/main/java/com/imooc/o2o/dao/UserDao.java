package com.imooc.o2o.dao;

import com.imooc.o2o.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/20 21:30:24
 * @description
 */
@Repository("userDao")
public interface UserDao {
	//根据查询条件分页返回用户信息列表
	List<User> queryUserList(@Param("user") User user, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	//根据查询条件返回总数，配合queryUserList使用
	int queryUserCount(@Param("user") User user);

	//通过用户Id查询用户
	User queryUserById(long userId);

	//通过用户名查询用户
	User queryUserByName(String userName);

	//添加用户信息
	int insertUser(User user);

	//修改用户信息
	int updateUser(User user);

}
