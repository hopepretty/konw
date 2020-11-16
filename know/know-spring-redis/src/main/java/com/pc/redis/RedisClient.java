package com.pc.redis;

import com.pc.util.JsonUtil;
import com.pc.util.StringUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * 模拟redis客户端
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class RedisClient {

	/**
     * 域
	 */
	private String region = "";

	/**
	 * redis模板对象
	 */
	private StringRedisTemplate redisTemplate;

	public RedisClient(String region, @NonNull StringRedisTemplate redisTemplate) {
		if (StringUtil.isNotEmpty(region)) {
			this.region = region + ":";
		}
		this.redisTemplate = redisTemplate;
	}

	//==========  common ======//

	/**
	 * 添加前缀
	 *
	 * @param key
	 * @return
	 */
	protected String getKeyName(String key) {
		return this.region + key;
	}

	/**
	 * 重置指定key有效期
	 *
	 * @param key
	 * @param second
	 * @return
	 */
	public boolean expire(String key, long second) {
		if (StringUtil.isEmpty(key) || second < 0) {
			return false;
		}
		try {
			Boolean expire = redisTemplate.expire(getKeyName(key), second, TimeUnit.SECONDS);
			return expire != null && expire;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取指定key有效期
	 *
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		if (StringUtil.isEmpty(key)) {
			throw new RuntimeException("key不能为空");
		}
		return redisTemplate.getExpire(getKeyName(key), TimeUnit.SECONDS);
	}

	/**
	 * 判断是否存在指定key
	 *
	 * @param key
	 * @return
	 */
	public boolean exist(String key) {
		if (StringUtil.isEmpty(key)) {
			return false;
		}
		try {
			Boolean aBoolean = redisTemplate.hasKey(getKeyName(key));
			return aBoolean != null && aBoolean;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	//==========  common ======//

	//================ String ===================///

	/**
	 * String
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean set(String key, T value) {
		return set(key, value, 0, false);
	}

	/**
	 * String
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @param second 单位秒
	 * @return
	 */
	public <T> boolean set(String key, T value, long second) {
		return set(key, value, second, false);
	}

	/**
	 * String
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean setIfAbsent(String key, T value) {
		return set(key, value, 0, true);
	}

	/**
	 * String
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @param second 单位秒
	 * @return
	 */
	public <T> boolean setIfAbsent(String key, T value, long second) {
		return set(key, value, second, true);
	}

	/**
	 * String
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 * @param second 单位秒
	 * @param override
	 * @return
	 */
	private <T> boolean set(String key, T value, long second, boolean override) {
		if (StringUtil.isEmpty(key) || value == null) {
			return false;
		}
		try {
			String s = JsonUtil.objectToString(value);
			String keyName = getKeyName(key);
			ValueOperations<String, String> operations = redisTemplate.opsForValue();
			if (override) {
				if (second > 0) {
					operations.setIfAbsent(keyName, s, second, TimeUnit.SECONDS);
				} else {
					operations.setIfAbsent(keyName, s);
				}
			} else {
				if (second > 0) {
					operations.set(keyName, s, second, TimeUnit.SECONDS);
				} else {
					operations.set(keyName, s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取value
	 *
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		if (StringUtil.isEmpty(key) || clazz == null) {
			return null;
		}
		try {
			String s = redisTemplate.opsForValue().get(getKeyName(key));
			return JsonUtil.stringToObject(s, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 递增
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public long incr(String key, long value) {
		if (StringUtil.isEmpty(key) || value <= 0) {
			throw new RuntimeException("key不能为空,并且递增值必须大于0");
		}
		try {
			return redisTemplate.opsForValue().increment(getKeyName(key), value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("incr操作失败");
		}
	}

	/**
	 * 递减
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public long decr(String key, long value) {
		if (StringUtil.isEmpty(key) || value <= 0) {
			throw new RuntimeException("key不能为空,并且递减值必须大于0");
		}
		try {
			return redisTemplate.opsForValue().decrement(getKeyName(key), value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("decr操作失败");
		}
	}

	//================ String ===================///

	//=============== Map ==========================///\

	/**
	 * map获取指定项值
	 *
	 * @param key
	 * @param item
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T hget(String key, String item, Class<T> clazz) {
		if (StringUtil.isEmpty(key) || clazz == null) {
			return null;
		}
		try {
			Object o = redisTemplate.opsForHash().get(getKeyName(key), item);
			return JsonUtil.stringToObject(JsonUtil.objectToString(o), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 设置map类型键值
	 *
	 * @param key
	 * @param item
	 * @param value
	 * @param <T>
	 * @return
	 */
	public <T> boolean hset(String key, String item, T value) {
		return hset(key, item, value, false);
	}

	/**
	 * 设置map类型键值
	 *
	 * @param key
	 * @param item
	 * @param value
	 * @param <T>
	 * @return
	 */
	public <T> boolean hsetIfAbsent(String key, String item, T value) {
		return hset(key, item, value, true);
	}

	/**
	 * 设置map类型键值
	 *
	 * @param key
	 * @param item
	 * @param value
	 * @param override
	 * @param <T>
	 * @return
	 */
	private <T> boolean hset(String key, String item, T value, boolean override) {
		if (StringUtil.isEmpty(key) || StringUtil.isEmpty(item)) {
			return false;
		}
		try {
			HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
			if (override) {
				operations.putIfAbsent(getKeyName(key), item, value);
			} else {
				operations.put(getKeyName(key), item, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
