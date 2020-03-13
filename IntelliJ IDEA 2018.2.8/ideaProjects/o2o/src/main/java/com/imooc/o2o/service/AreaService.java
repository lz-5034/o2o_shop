package com.imooc.o2o.service;

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/18 23:05:19
 * @description
 */
public interface AreaService {
    //定义常量作为Redis数据库的key
    String AREA_LIST_KEY = "arealist";

    List<Area> getAllArea();
}
