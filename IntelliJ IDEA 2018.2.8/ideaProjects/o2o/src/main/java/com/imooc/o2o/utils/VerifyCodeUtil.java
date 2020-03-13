package com.imooc.o2o.utils;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LZ
 * @date 2020/2/23 12:01:05
 * @description 验证码校验工具
 */
public class VerifyCodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取存储在session域中正确的验证码值
        String verifyCodeExpected = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码值
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
