package com.sbatis.redis.handle;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
	
	/**
	 * 根据 key 和 value 添加到 redis 数据库
	 * @author HuangLongPu
	 * @param key 保存到 redis 数据库的 key
	 * @param mk map 的 key
	 * @param mv map 的 value
	 */
	public void set(String key, MK mk, MV mv) {
		this.redisTemplate.opsForHash().put(key, mk, mv);
	}
	
	/**
	 * 根据 key 和 map 添加到 redis 数据库
	 * @author HuangLongPu
	 * @param key
	 * @param value
	 */
	public void set(String key, Map<MK, MV> value) {
		this.redisTemplate.opsForHash().putAll(key, value);
	}
	
	/**
	 * 根据 key 获取 map 数据
	 * @author HuangLongPu
	 * @param key
	 * @return
	 */
	public Map<MK, MV> get(String key) {
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.entries(key);
	}
	
	/**
	 * 根据 key 和 map key 获取 value 值
	 * @author HuangLongPu
	 * @param key
	 * @param mk
	 * @return
	 */
	public MV get(String key, MK mk) {
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.get(key, mk);
	}
	
	/**
	 * 根据 key 获取 map 的 value 集合
	 * @author HuangLongPu
	 * @param key
	 * @return
	 */
	public List<MV> list(String key) {
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.values(key);
	}
	
	/**
	 * 根据 key 和 map 的 key，获取 value 集合
	 * @author HuangLongPu
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public List<MV> list(String key, List<MK> hashKeys) {
		HashOperations<String, MK, MV> opsForMap = this.redisTemplate.opsForHash();
		return opsForMap.multiGet(key, hashKeys);
	}
	
	/**
	 * 根据 key 获取 map 的长度
	 * @author HuangLongPu
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		return this.redisTemplate.opsForHash().size(key);
	}
	
	/**
	 * 根据 key 和 map 的 key，进行删除， map 的 key 为可变数组
	 * @author HuangLongPu
	 * @param key
	 * @param hashKeys
	 */
	@SuppressWarnings("unchecked")
	public void remove(String key, MK... hashKeys) {
		this.redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**
	 * 根据 key 从 redis 数据库删除
	 * @author HuangLongPu
	 * @param key
	 */
	public void delete(String key) {
		this.redisTemplate.delete(key);
	}
}
