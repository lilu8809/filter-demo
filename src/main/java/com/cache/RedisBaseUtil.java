package com.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * RedisUtil
 * 
 * @author lilu
 * @category 抽象类,定义了各种redis操作方法的实现细节
 */
public abstract class RedisBaseUtil {
	
	protected abstract Jedis getRedisConnection();
	
	public String get(String key) {
		Jedis conn = getRedisConnection();
		String result = null;
		try {
			result = conn.get(key);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public boolean hexist(String key, String field) {
		Jedis conn = getRedisConnection();
		boolean result = false;
		try {
			result = conn.hexists(key, field);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public String hget(String key, String field) {
		Jedis conn = getRedisConnection();
		String result = null;
		try {
			result = conn.hget(key, field);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long hgetLong(String key, String field) {
		long result = 0;
		try {
			result = Long.parseLong(hget(key, field));
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getInt(String key) {
		int result = 0;
		try {
			result = Integer.parseInt(get(key));
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String set(String key, String value) {
		Jedis conn = getRedisConnection();
		String result = null;
		try {
			result = conn.set(key, value);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public String multiSet(String... keysvalues) {
		Jedis conn = getRedisConnection();
		String result = null;
		try {
			result = conn.mset(keysvalues);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long hset(String key, String field, String value) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.hset(key, field, value);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public boolean exists(String key) {
		Jedis conn = getRedisConnection();
		boolean result = false;
		try {
			result = conn.exists(key);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long delete(String... keys) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.del(keys);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long sadd(String key, Object value) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.sadd(key, String.valueOf(value));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long zadd(String key, Object value, double score) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.zadd(key, score, String.valueOf(value));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public Set<String> getZSet(String key) {
		Jedis conn = getRedisConnection();
		Set<String> result = null;
		try {
			result = conn.zrange(key, 0, -1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public Set<String> getZRevSet(String key) {
		Jedis conn = getRedisConnection();
		Set<String> result = null;
		try {
			result = conn.zrevrange(key, 0, -1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public Set<String> getZSetByScore(String key, int... scores) {
		Jedis conn = getRedisConnection();
		Set<String> result = new HashSet<String>();
		try {
			for(int i = 0; i < scores.length; i++) {
				result.addAll(conn.zrangeByScore(key, scores[i], scores[i]));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public int getZSetSizeByScore(String key, int... scores) {
		return getZSetByScore(key, scores).size();
	}
	
	public Map<String, Integer> getZScoreMap(String key, int... scores) {
		Jedis conn = getRedisConnection();
		Map<String, Integer> scoreMap = new HashMap<String, Integer>();
		try {
			for(int i = 0; i < scores.length; i++) {
				Set<Tuple> set = conn.zrangeByScoreWithScores(key, scores[i], scores[i]);
				for(Tuple t : set) {
					scoreMap.put(t.getElement(), (int)t.getScore());
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return scoreMap;
	}
	
	public int getZScore(String key, String member) {
		Jedis conn = getRedisConnection();
		int result = 0;
		try {
			Double d = conn.zscore(key, member);
			if(d != null) {
				result = d.intValue();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long srem(String key, String... members) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.srem(key, members);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long zrem(String key, String... members) {
		Jedis conn = getRedisConnection();
		long result = 0;
		try {
			result = conn.zrem(key, members);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public Set<String> getSet(String key) {
		Jedis conn = getRedisConnection();
		Set<String> result = null;
		try {
			result = conn.smembers(key);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public Set<String> keys(String pattern) {
		Jedis conn = getRedisConnection();
		Set<String> result = null;
		try {
			result = conn.keys(pattern);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	public long expire(String key, int seconds) {
		long result = 0;
		Jedis conn = getRedisConnection();
		try {
			result = conn.expire(key, seconds);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		return result;
	}
}
