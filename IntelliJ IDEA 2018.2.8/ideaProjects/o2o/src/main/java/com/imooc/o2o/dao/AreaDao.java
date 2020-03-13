package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 林仲
 * @date 2020/2/18 22:13:17
 * @description
 */
@Repository("areaDao")
public interface AreaDao {
    List<Area> selectAllArea();
}
