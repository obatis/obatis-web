package com.sbatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.sbatis.validate.ValidateTool;

import java.lang.reflect.Type;

/**
 * 主要对BigInt类型进行转换处理
 * @author HuangLongPu
 */
public class HttpMessageBigIntConvertSerializer implements ObjectSerializer {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
		SerializeWriter out = serializer.getWriter();
		if (ValidateTool.isEmpty(object)) {
			serializer.getWriter().writeNull();
			return;
		} 
		
		String value = object.toString();
		// 如果长度达到28位，说明是生成的number
		if(value.length() >= 28) {
			out.write("\"" + value + "\"");
			return;
		}
		out.write(value);;
	}

	
}
