package com.imooc.o2o.dao;

import com.imooc.o2o.entity.HeadLine;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/3/4 10:34:18
 * @description
 */
@Repository("headLineDao")
public interface HeadLineDao {
    //根据传入的条件进行查询
    List<HeadLine> selectHeadLineList(HeadLine headLine);
}
