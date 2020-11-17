package com.obatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.obatis.tools.ValidateTool;

import java.lang.reflect.Type;

/**
 * 主要对大整型Number类型进行转换处理，防止科学计数，比如生成的28为number串码，否则前端接收会出现科学计数的现象
 * @author HuangLongPu
 */
public class HttpMessageNumberConvertSerializer implements ObjectSerializer {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
		if (ValidateTool.isEmpty(object)) {
			serializer.out.writeNull();
			return;
		} 
		
		String value = object.toString();
		// 如果长度达到28位，说明是生成的number
		if(value.length() >= 18) {
			serializer.out.write("\"" + value + "\"");
			return;
		}
		serializer.out.write(value);
	}

	
}
