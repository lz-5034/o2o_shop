package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/3/4 11:43:44
 * @description
 */
public interface HeadLineService {
    //定义常量作为Redis数据库的key
    String HEAD_LINE_LIST_KEY = "headlinelist";

    List<HeadLine> getHeadLineList(HeadLine headLine);
}
