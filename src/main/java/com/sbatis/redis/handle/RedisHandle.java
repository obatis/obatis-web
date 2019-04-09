package com.sbatis.redis.handle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.sbatis.convert.CommonConvert;
import com.sbatis.convert.JsonCommonConvert;
import com.sbatis.core.exception.CommonException;
import com.sbatis.validate.ValidateTool;
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

	/**
	 * 设置常规对象
	 * @author HuangLongPu
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置常规对象，可以设置过期时间，单位为秒
	 * @author HuangLongPu
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
	 * @author HuangLongPu
	 * @param key
	 * @param list
	 */
    public void setList(String key, List<?> list){

    	if(ValidateTool.isEmpty(key)) {
    		throw new CommonException("error : key is empty!");
    	}
    	if(list == null) {
    		throw new CommonException("error : list is empty!");
    	}
    	String data = JsonCommonConvert.objConvertJson(list);
    	this.set(key, data);
    }

	/**
	 * 将list存储于redis中，需传入泛型，支持过期时间
	 * @author HuangLongPu
	 * @param key
	 * @param list
	 * @param timeout
	 */
    public void setList(String key, List<?> list, int timeout){

    	if(ValidateTool.isEmpty(key)) {
    		throw new CommonException("error : key is empty!");
    	}
    	if(list == null) {
    		throw new CommonException("error : list is empty!");
    	}
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    	String data = JsonCommonConvert.objConvertJson(list);
    	this.set(key, data);
    }

    /**
     * 通过key获取list数据，由于需要转list的数据，需要传入class进行转换
	 * @author HuangLongPu
     * @param key
     * @param cls
     * @return
     */
    public<M> List<M> getList(String key, Class<M> cls){
    	if(ValidateTool.isEmpty(key)) {
    		throw new CommonException("error : key is empty!!!");
    	}
    	Object data = this.get(key);
    	String jsonStr = CommonConvert.toString(data);
    	return JsonCommonConvert.jsonConvertList(jsonStr, cls);
    }
}
