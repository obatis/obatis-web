package com.obatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.obatis.convert.date.DefaultDateConstant;
import com.obatis.tools.ValidateTool;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 主要对 LocalDateTime 类型进行转换处理
 * @author HuangLongPu
 */
public class HttpMessageLocalDateConvertSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.getWriter();
        if (ValidateTool.isEmpty(object)) {
            serializer.getWriter().writeNull();
            return;
        }

        LocalDate result = (LocalDate) object;
        out.write(result.format(DateTimeFormatter.ofPattern(DefaultDateConstant.DATE_PATTERN)));
    }


}
