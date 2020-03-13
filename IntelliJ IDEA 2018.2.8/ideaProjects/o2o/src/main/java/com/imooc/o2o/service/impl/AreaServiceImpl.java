package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/18 23:06:18
 * @description 区域信息，引入Redis
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    //日志
    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    @Transactional
    public List<Area> getAllArea() {
        //声明需要返回的值
        List<Area> areaList = null;
        //创建Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        String key = AREA_LIST_KEY;
        try {
            //当Redis里面没有这个key，则设置键值对
            if (!jedisKeys.exists(key)) {
                areaList = areaDao.selectAllArea();
                String value = objectMapper.writeValueAsString(areaList);
                jedisStrings.set(key, value);
            } else {    //已经存在这个键值对的情况下，则获取值
                String value = jedisStrings.get(key);
                //设置类型转换的方式 --》 是list集合，且是area类的
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
                //根据json字符串和转换方式，将值（area）取出来封装到list集合中
                areaList = objectMapper.readValue(value, javaType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new AreaOperationException(e.toString());
        }
        return areaList;
    }
}
