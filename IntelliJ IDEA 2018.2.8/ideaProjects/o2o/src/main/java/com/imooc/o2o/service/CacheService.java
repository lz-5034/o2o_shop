package com.imooc.o2o.service;

/**
 * @author 林仲
 * @date 2020/3/11 22:41:38
 * @description
 */
public interface CacheService {
    //根据传入的key前缀，将以keyPrefix为前缀的所有key_value删除
    void removeFromCache(String keyPrefix);
}
