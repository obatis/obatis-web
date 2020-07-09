package com.obatis.config.message;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.obatis.convert.date.DefaultDateConstant;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 主要对 LocalDate 类型进行转换处理
 * @author HuangLongPu
 */
public class HttpMessageDateConvertSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object == null) {
            serializer.out.writeNull();
        } else {
            Date date = (Date)object;
            SimpleDateFormat format = new SimpleDateFormat(DefaultDateConstant.DATE_TIME_PATTERN);
            serializer.write(format.format(date));
        }
    }


}
