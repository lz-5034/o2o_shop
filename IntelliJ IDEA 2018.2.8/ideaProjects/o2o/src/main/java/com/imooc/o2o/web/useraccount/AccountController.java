package com.imooc.o2o.web.useraccount;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LZ
 * @date 2020/2/22 21:02:32
 * @description 用户账号操作路由器
 */
@Controller("accountController")
@RequestMapping("/local")
public class AccountController {
	//绑定帐号页
	@RequestMapping(value = "/accountbind", method = RequestMethod.GET)
	private String accountbind() {
		return "local/accountbind";
	}
	//修改密码页
	@RequestMapping(value = "/changepsw", method = RequestMethod.GET)
	private String changepsw() {
		return "local/changepsw";
	}	
	//登录页
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login() {
		return "local/login";
	}	
}
