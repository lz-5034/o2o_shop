package com.imooc.o2o.utils;

/**
 * @author LZ
 * @date 2020/2/20 23:28:06
 * @description 路径工具类
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");//获取当前系统路径分隔符
    //设置图片保存路径
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");  //获取当前系统名称
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "d:/project_o2o/image";
        } else {
            basePath = "/home/lz/image";
        }
        basePath = basePath.replace("/", seperator);    //将路径分割符设置与当前系统相同
        return basePath;
    }

    //设置店铺图片保存路径
    public static String getShopImgPath(long shopId) {
        String basePath = "/upload/item/shop/" + shopId + "/";
        return basePath.replace("/", seperator);
    }
}
