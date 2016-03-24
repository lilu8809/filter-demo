package com.cache;

import redis.clients.jedis.Jedis;

public class RedisCacheUtil extends RedisBaseUtil {
	
	@Override
	protected Jedis getRedisConnection() {
		return RedisCachePoolManager.getConnection();
	}
}
