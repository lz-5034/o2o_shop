package com.imooc.o2o.utils;

/**
 * @author LZ
 * @date 2020/2/25 10:31:19
 * @description
 */
public class PageCalculator {
    /**
     * 通过用户传入的页数和每页查询的个数，转换成数据库查询时的行数索引
     * @param pageIndex 用户传入希望查询的第几页
     * @param pageSize  每页查询的个数
     * @return
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
