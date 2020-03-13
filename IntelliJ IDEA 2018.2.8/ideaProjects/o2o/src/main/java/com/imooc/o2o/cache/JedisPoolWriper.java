package com.imooc.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 强指定redis的JedisPool接口构造函数，这样才能在centos成功创建jedispool
 * @author xiangze
 */
public class JedisPoolWriper {
	/** Redis连接池对象 */
	private JedisPool jedisPool;

	/**
	 * 通过该类的构造函数 进行相关配置，来创建Redis的连接池对象
	 * @param poolConfig jedis的相关数据库配置
	 * @param host	服务器id地址
	 * @param port	Redis端口号
	 */
	public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
			final int port) {
		try {
			jedisPool = new JedisPool(poolConfig, host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Redis连接池对象
	 * @return
	 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	/**
	 * 注入Redis连接池对象
	 * @param jedisPool
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
