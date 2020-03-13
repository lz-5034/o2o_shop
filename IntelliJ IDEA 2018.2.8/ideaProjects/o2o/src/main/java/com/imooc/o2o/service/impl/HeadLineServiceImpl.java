package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LZ
 * @date 2020/3/4 11:45:16
 * @description 头条，引入Redis
 */
@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    //日志
    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    /**
     * 根据传来的条件，返回头条集合
     * @param headLine
     * @return
     */
    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLine) {
        //声明返回对象
        List<HeadLine> headLineList = null;
        //创建Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        String key = HEAD_LINE_LIST_KEY;
        //拼接key，因为头条的状态可能是0不可用，可能是1可用，将key设置成两种
        if (headLine != null && headLine.getEnableStatus() != null) {
            key = key + "_" + headLine.getEnableStatus();
        }
        try {
            //如果Redis中不存在该key的情况
            if (!jedisKeys.exists(key)) {
                //查询出数据
                headLineList = headLineDao.selectHeadLineList(headLine);
                //将数据转换成json格式
                String value = objectMapper.writeValueAsString(headLineList);
                //Redis数据库设置键值对
                jedisStrings.set(key, value);
            } else {    //已经存在该key的情况
                //根据key从Redis中取出数据
                String value = jedisStrings.get(key);
                //设置从json转换成java类型的转换格式
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
                //根据json和转换格式，将数据读出来
                headLineList = objectMapper.readValue(value, javaType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new HeadLineOperationException(e.toString());
        }
        return headLineList;
    }
}
