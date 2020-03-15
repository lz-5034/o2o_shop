package com.imooc.o2o.web.useraccount;

import com.imooc.o2o.dao.UserDao;
import com.imooc.o2o.dto.AccountExecution;
import com.imooc.o2o.entity.Account;
import com.imooc.o2o.entity.User;
import com.imooc.o2o.enums.AccountStateEnum;
import com.imooc.o2o.exceptions.AccountOperationException;
import com.imooc.o2o.service.AccountService;
import com.imooc.o2o.utils.HttpServletRequestUtil;
import com.imooc.o2o.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/2/22 21:02:32
 * @description 用户账号操作
 */
@Controller("accountOperationController")
@RequestMapping(value = "/local", method = { RequestMethod.GET, RequestMethod.POST })
public class AccountOperationController {
	@Autowired
	private AccountService AccountService;
	@Autowired
	private UserDao userDao;

	/**
	 * 将用户信息与平台帐号绑定
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindAccount", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> bindAccount(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		if (!VerifyCodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取输入的帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取用户状态码
		Integer status = HttpServletRequestUtil.getInt(request, "usertype");
		// 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || "".equals(user)) {
			// 非空判断，要求帐号密码以及当前的用户session非空
			if (userName != null && password != null) {
				Account Account = AccountService.checkAccount(userName, password, status);
				if(Account == null){
					// 创建Account对象并赋值
					Account = new Account();
					Account.setUsername(userName);
					Account.setPassword(password);
					user = new User();
					user.setName(userName);
					user.setCreateTime(new Date());
					user.setLastEditTime(new Date());
					user.setEnableStatus(1);
					user.setUserType(status);
					int effectedNum = userDao.insertUser(user);
					if (effectedNum <= 0) {
						modelMap.put("msg", "绑定用户信息错误");
					}
					Account.setUser(user);
				}
				// 绑定帐号
				AccountExecution le = AccountService.bindAccount(Account);
				if (le.getState() == AccountStateEnum.SUCCESS.getState()) {
					request.getSession().setAttribute("user",Account.getUser());
					modelMap.put("success", true);
					modelMap.put("msg","绑定成功！");
				} else if(le.getState() == AccountStateEnum.ONLY_ONE_ACCOUNT.getState()) {
					request.getSession().setAttribute("user",Account.getUser());
					modelMap.put("success", true);
					modelMap.put("msg","帐号已绑定，已自动登录");
				}else{
					modelMap.put("success", false);
					modelMap.put("msg", le.getStateInfo());
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "用户名和密码均不能为空");
			}

		}else{
			modelMap.put("success", true);
			modelMap.put("msg","帐号已绑定，已自动登录");
		}
		return modelMap;
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		if (!VerifyCodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取原密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取新密码
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		// 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
		User user = (User) request.getSession().getAttribute("user");
		// 非空判断，要求帐号新旧密码以及当前的用户session非空，且新旧密码不相同
		if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& !password.equals(newPassword)) {
			try {
				// 查看原先帐号，看看与输入的帐号是否一致，不一致则认为是非法操作
				Account Account = AccountService.getAccountByUserId(user.getUserId());
				if (Account == null || !Account.getUsername().equals(userName)) {
					// 不一致则直接退出
					modelMap.put("success", false);
					modelMap.put("msg", "输入的帐号非本次登录的帐号");
					return modelMap;
				}
				// 修改平台帐号的用户密码
				AccountExecution le = AccountService.modifyAccount(user.getUserId(), userName, password,
						newPassword);
				if (le.getState() == AccountStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("msg", "修改密码成功！");
				} else {
					modelMap.put("success", false);
					modelMap.put("msg", le.getStateInfo());
				}
			} catch (AccountOperationException e) {
				modelMap.put("success", false);
				modelMap.put("msg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("msg", "请输入密码");
		}
		return modelMap;
	}

	/**
	 * 登录功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logincheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取是否需要进行验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !VerifyCodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取输入的帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取用户的身份状态
		int userType = HttpServletRequestUtil.getInt(request, "userType");
		// 非空校验
		if (userName != null && password != null && userType > 0) {
			// 传入帐号和密码去获取平台帐号信息
			Account Account = AccountService.checkAccount(userName, password, userType);
			if (Account != null) {
				// 若能取到帐号信息则登录成功
				modelMap.put("success", true);
				// 同时在session里设置用户信息
				request.getSession().setAttribute("user", Account.getUser());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	/**
	 * 当用户点击登出按钮的时候注销session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 将用户session置为空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
