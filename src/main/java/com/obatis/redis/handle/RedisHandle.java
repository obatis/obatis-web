package com.obatis.redis.handle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.obatis.convert.CommonConvert;
import com.obatis.convert.JsonCommonConvert;
import com.obatis.core.exception.HandleException;
import com.obatis.validate.ValidateTool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 针对存储String类型的数据，兼容list的存储
 * @author HuangLongPu
 */
@Component
public class RedisHandle {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public void expire(String key, int timeout) {
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置常规对象
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置常规对象，可以设置过期时间，单位为秒
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key, Object value, int timeout) {
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		this.redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 从redis获取常规对象的值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}
	
	public void remove(String key) {
		this.redisTemplate.delete(key);
	}
	
	/**
	 * 将list存储于redis中，需传入泛型
	 * @param key
	 * @param list
	 */
    public void setList(String key, List<?> list){
    	this.validateDate(key, list);
    	String data = JsonCommonConvert.objConvertJson(list);
    	this.set(key, data);
    }

	/**
	 * 将list存储于redis中，需传入泛型，支持过期时间
	 * @param key
	 * @param list
	 * @param timeout
	 */
    public void setList(String key, List<?> list, int timeout){
    	this.validateDate(key, list);
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    	String data = JsonCommonConvert.objConvertJson(list);
    	this.set(key, data);
    }

    private void validateDate(String key, List<?> list) throws HandleException {
		if(ValidateTool.isEmpty(key)) {
			throw new HandleException("error : key is empty!");
		}
		if(list == null) {
			throw new HandleException("error : list is empty!");
		}
	}

    /**
     * 通过key获取list数据，由于需要转list的数据，需要传入class进行转换
     * @param key
     * @param cls
     * @return
     */
    public<M> List<M> getList(String key, Class<M> cls){
    	if(ValidateTool.isEmpty(key)) {
    		throw new HandleException("error : key is empty!!!");
    	}
    	Object data = this.get(key);
    	String jsonStr = CommonConvert.toString(data);
    	return JsonCommonConvert.jsonConvertList(jsonStr, cls);
    }
}
