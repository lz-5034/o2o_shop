package com.imooc.o2o.service.impl;

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author LZ
 * @date 2020/3/11 22:44:15
 * @description
 */
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        //遍历符合条件的key
        Set<String> keys = jedisKeys.keys(keyPrefix + "*");
        //将符合条件的key_value删除
        for (String key : keys) {
            jedisKeys.del(key);
        }
    }
}
