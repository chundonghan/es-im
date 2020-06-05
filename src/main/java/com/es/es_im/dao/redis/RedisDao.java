package com.es.es_im.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
@Component
public class RedisDao{
	private static final Logger logger = LoggerFactory.getLogger(RedisDao.class);
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public void set(String key, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String ret =null;
		try {
			 ret = jedis.set(key, value);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		logger.info("return:{}",ret);
		return ;
	}
	public void expire(String key,int seconds) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			 jedis.expire(key, seconds);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return ;
	}
	public String get(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String result = "";
		try {
			result = jedis.get(key);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;

	}

	public Long hset(String key, String field, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		Long result = null;
		try {
			result = jedis.hset(key, field, value);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public Long hset(byte[] key, byte[]  field, byte[]  value) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		Long result = null;
		try {
			result = jedis.hset(key, field, value);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public String hget(String key, String field) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		String result = null;
		try {
			result = jedis.hget(key, field);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public byte[] hget(byte[] key, byte[] field) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		byte[] result = null;
		try {
			result = jedis.hget(key, field);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public long hdel(byte[] key, byte[]... fields) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		long result = 0l;
		try {
			result = jedis.hdel(key, fields);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public List<String> lrange(String key, long start, long end) {
		ShardedJedis jedis = shardedJedisPool.getResource();

		List<String> result = null;
		try {
			result = jedis.lrange(key, start, end);
			;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Long del(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long result = null;
		try {
			result = jedis.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public void rpush(String key, String... value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.rpush(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return;
	}

	public void lpush(String key, String... value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return;
	}

	public void lpop(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.lpop(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return;
	}

	public void rpop(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.rpop(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return;
	}

	public void lrem(String key, int count, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.lrem(key, count, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return;
	}
	public long llen(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		long len = 0 ;
		try {
			len = jedis.llen(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return len;
	}
	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			this.set(key, value);
			jedis.expire(key, liveTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		boolean result = false;
		try {
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 获取一个key的模糊匹配总数
	 * 
	 * @param key
	 * @return
	 */
	public int getKeyCount(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Set<String> result = null;
		try {
			result = jedis.getShard(key).keys(key + "*");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result.size();
	}

	/**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Set<String> result = null;
		try {
			result = jedis.getShard("").keys("*");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result.size();
	}

	public long sadd(String key, String... value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		long result = 0l;
		try {
			result = jedis.sadd(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Boolean sismember(String key, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Boolean result = false;
		try {
			result = jedis.sismember(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	/**
	 * 从集合中删除指定值
	 * @param key
	 * @param value
	 * @return
	 */
	public long srem(String key,String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		long result = 0;
		try {
			result = jedis.srem(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	
	public Set<String> smembers(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Set<String> result = null;
		try {
			result = jedis.smembers(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public long zadd(String key, int sequence, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		long result = 0l;
		try {
			result = jedis.zadd(key, sequence, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Set<String> zrange(String key, long start, long end) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Set<String> result = null;
		try {
			result = jedis.zrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public String hmset(String key, Map<String, String> map) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String result = null;
		try {
			result = jedis.hmset(key, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Map<String, String> result = null;
		try {
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}
	public long incr(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		long count = 0l;
		try {
			count = jedis.incr(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return count;
	}
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
}

