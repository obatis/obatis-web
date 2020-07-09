package com.obatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.obatis.convert.date.DefaultDateConstant;
import com.obatis.tools.ValidateTool;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 主要对 LocalDateTime 类型进行转换处理
 * @author HuangLongPu
 */
public class HttpMessageLocalDateConvertSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (ValidateTool.isEmpty(object)) {
            serializer.out.writeNull();
            return;
        }

        LocalDate result = (LocalDate) object;
        serializer.out.writeString(result.format(DateTimeFormatter.ofPattern(DefaultDateConstant.DATE_PATTERN)));
    }


}
