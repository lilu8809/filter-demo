package com.cache;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisPoolManager
 * 
 * @author abiao
 */
public class RedisCachePoolManager {
	
	private static final Logger LOG = Logger.getLogger(RedisCachePoolManager.class);
	private static JedisPool pool;
	
	static {
		try {
			ConfigUtil config = ConfigUtil.load("redis.properties");
			
			JedisPoolConfig cfg = new JedisPoolConfig();
			cfg.setMaxTotal(config.getInt("redis.pool.maxActive"));
			cfg.setMaxIdle(config.getInt("redis.pool.maxIdle"));
			cfg.setMaxWaitMillis(config.getInt("redis.pool.maxWait"));
			cfg.setTestOnBorrow(config.getBool("redis.pool.testOnBorrow"));
			cfg.setTestOnReturn(config.getBool("redis.pool.testOnReturn"));
			pool = new JedisPool(cfg, config.get("redis.ip"), config.getInt("redis.port"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Jedis getConnection() {
		Jedis conn = null;
		try {
			conn = pool.getResource();
		}
		catch(Exception e) {
			LOG.error("redis error");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void initPool() {
		LOG.debug("init redis cache pool...");
		getConnection();
		LOG.debug("done\n");
	}
	
	public static void closePool() {
		pool.destroy();
	}
}
