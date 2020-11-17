package com.obatis.config.time;

import com.obatis.convert.date.DateConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * 时间转换处理类，体现为前端传入字符串时间类型时，也可以正常接收
 */
@Configuration
public class LocalDateTimeConverterConfig implements Converter<String, LocalDateTime> {
	
    @Override
    public LocalDateTime convert(String source) {
        return DateConvert.parseDateTime(source);
    }

}
