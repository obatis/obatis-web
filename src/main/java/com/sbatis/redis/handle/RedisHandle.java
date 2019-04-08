package com.sbatis.redis.handle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.sbatis.convert.CommonConvert;
import com.sbatis.convert.JsonCommonConvert;
import com.sbatis.core.exception.BusinessException;
import com.sbatis.validate.ValidateTool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 针对存储String类型的数据，兼容list的存储
 * @author HuangLongPu
 *
 */
@Component
public class RedisHandle {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	public void set(String key, Object value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, Object value, int timeout) {
		this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		this.redisTemplate.opsForValue().set(key, value);
	}

	public Object get(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}
	
	public void remove(String key) {
		this.redisTemplate.delete(key);
	}
	
	/**
	 * 将list放入redis，接受T泛型
	 * @param key
	 * @param list
	 */
    public<T> void setList(String key, List<T> list){

    	if(ValidateTool.isEmpty(key)) {
    		throw new BusinessException("error : key is empty!!!");
    	}
    	if(list == null) {
    		throw new BusinessException("error : list is empty!!!");
    	}
    	String data = JsonCommonConvert.objConvertJson(list);
    	this.set(key, data);
    }
    
    /**
     * 通过key获取list数据，由于需要转list的数据，需要传入class
     * @param key
     * @param cls
     * @return
     */
    public<T> List<T> getList(String key, Class<T> cls){
    	if(ValidateTool.isEmpty(key)) {
    		throw new BusinessException("error : key is empty!!!");
    	}
    	Object data = this.get(key);
    	String jsonStr = CommonConvert.toString(data);
    	return JsonCommonConvert.jsonConvertList(jsonStr, cls);
    }
}
