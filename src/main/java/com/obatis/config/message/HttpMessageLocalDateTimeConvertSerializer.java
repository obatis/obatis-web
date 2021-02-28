package com.obatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.obatis.convert.date.DateConstant;
import com.obatis.tools.ValidateTool;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 主要对 LocalDateTime 类型进行转换处理
 * @author HuangLongPu
 */
public class HttpMessageLocalDateTimeConvertSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (ValidateTool.isEmpty(object)) {
            serializer.out.writeNull();
            return;
        }

        LocalDateTime result = (LocalDateTime) object;
        serializer.out.writeString(result.format(DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_PATTERN)));
    }


}
