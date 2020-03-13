package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author 林仲
 * @date 2020/2/18 22:13:17
 * @description
 */
@Repository("accountDao")
public interface AccountDao {

	//通过帐号和密码查询对应信息，登录用
	Account selectAccountByNameAndPwd(@Param("username") String username, @Param("password") String password);

	//通过用户Id查询对应Account
	Account selectAccountByUserId(@Param("userId") long userId);

	//添加平台帐号
	int insertAccount(Account Account);

	//通过userId,username,password更改密码
	int updateAccount(@Param("userId") Long userId, @Param("username") String username,
                        @Param("password") String password, @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}
