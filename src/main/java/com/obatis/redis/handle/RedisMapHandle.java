package com.obatis.redis.handle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.obatis.common.exception.HandleException;
import com.obatis.tools.ValidateTool;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis缓存服务 map 操作处理类
 * @author HuangLongPu
 * @param <MK>
 * @param <MV>
 */
@Component
public class RedisMapHandle<MK, MV> {

	@Resource
	public RedisTemplate<String, Map<MK, MV>> redisTemplate;

	public void expire(String key, int timeout) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 根据 key 和 value 添加到 redis 数据库
	 * @param key 保存到 redis 数据库的 key
	 * @param mk map 的 key
	 * @param mv map 的 value
	 */
	public void set(String key, MK mk, MV mv) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		if(ValidateTool.isEmpty(mk)) {
			throw new HandleException("error : map key is empty");
		}
		this.redisTemplate.opsForHash().put(key, mk, mv);
	}

	/**
	 * 根据 key 和 value 添加到 redis 数据库，并设置过期时间
	 * @param key
	 * @param mk
	 * @param mv
	 * @param timeout
	 */
	public void set(String key, MK mk, MV mv, int timeout) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		this.redisTemplate.opsForHash().put(key, mk, mv);
		this.expire(key, timeout);
	}
	
	/**
	 * 根据 key 和 map 添加到 redis 数据库
	 * @param key
	 * @param value
	 */
	public void set(String key, Map<MK, MV> value) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		this.redisTemplate.opsForHash().putAll(key, value);
	}

	/**
	 * 根据 key 和 map 添加到 redis 数据库，并设置过期时间
	 * @param key
	 * @param value
	 */
	public void set(String key, Map<MK, MV> value, int timeout) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		this.redisTemplate.opsForHash().putAll(key, value);
		this.expire(key, timeout);
	}
	
	/**
	 * 根据 key 获取 map 数据
	 * @param key
	 * @return
	 */
	public Map<MK, MV> get(String key) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.entries(key);
	}


	
	/**
	 * 根据 key 和 map key 获取 value 值
	 * @param key
	 * @param mk
	 * @return
	 */
	public MV get(String key, MK mk) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		if(ValidateTool.isEmpty(mk)) {
			throw new HandleException("error : map key is empty");
		}
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.get(key, mk);
	}
	
	/**
	 * 根据 key 获取 map 的 value 集合
	 * @param key
	 * @return
	 */
	public List<MV> list(String key) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.values(key);
	}
	
	/**
	 * 根据 key 和 map 的 key，获取 value 集合
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public List<MV> list(String key, List<MK> hashKeys) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		if(ValidateTool.isEmpty(hashKeys)) {
			return list(key);
		} else {
			HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
			return opsForMap.multiGet(key, hashKeys);
		}
	}
	
	/**
	 * 根据 key 获取 map 的长度
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		return this.redisTemplate.opsForHash().size(key);
	}
	
	/**
	 * 根据 key 和 map 的 key，进行删除， map 的 key 为可变数组
	 * @param key
	 * @param hashKeys
	 */
	public void remove(String key, MK... hashKeys) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		if(ValidateTool.isEmpty(hashKeys) || hashKeys.length == 0) {
			this.delete(key);
		} else {
			this.redisTemplate.opsForHash().delete(key, hashKeys);
		}

	}
	
	/**
	 * 根据 key 从 redis 数据库删除
	 * @param key
	 */
	public void delete(String key) {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty");
		}
		this.redisTemplate.delete(key);
	}
}
