package com.pc.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis实例管理器
 *
 * @author pc
 * @Date 2020/11/16
 **/
public class RedisManager {

	private static StringRedisTemplate stringRedisTemplate;

	public RedisManager(StringRedisTemplate stringRedisTemplate) {
		RedisManager.stringRedisTemplate = stringRedisTemplate;
		System.out.println("init RedisManager");
	}

	/**
	 * 获取实例
	 *
	 * @return
	 */
	public static RedisClient getInstance() {
		return new RedisClient("", stringRedisTemplate);
	}

	/**
	 * 获取实例
	 *
	 * @param region
	 * @return
	 */
	public static RedisClient getInstance(String region) {
		return new RedisClient(region, stringRedisTemplate);
	}

}
