package com.imooc.o2o.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LZ
 * @date 2020/2/22 10:15:38
 * @description
 */
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key) {
        try {
            if (request.getParameter(key) != null && !(request.getParameter(key)).equals("")) {
                return Integer.parseInt(request.getParameter(key));
            }
            return -1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key) {
        try {
            if (request.getParameter(key) != null && !(request.getParameter(key)).equals("")) {
                return Long.parseLong(request.getParameter(key));
            }
            return -1L;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static double getDouble(HttpServletRequest request, String key) {
        try {
            if (request.getParameter(key) != null && !(request.getParameter(key)).equals("")) {
                return Double.parseDouble(request.getParameter(key));
            }
            return -1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            if (request.getParameter(key) != null && !(request.getParameter(key)).equals("")) {
               return Boolean.parseBoolean(request.getParameter(key));
            }
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key) {
        try {
            if (request.getParameter(key) != null && !(request.getParameter(key)).equals("")) {
                return request.getParameter(key);
            }
            return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
